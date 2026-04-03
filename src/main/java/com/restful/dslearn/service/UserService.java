package com.restful.dslearn.service;

import com.restful.dslearn.dto.UserDTO;
import com.restful.dslearn.dto.UserRequestDTO;
import com.restful.dslearn.entity.Role;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.RoleRepository;
import com.restful.dslearn.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return toDTO(user);
    }

    @Transactional
    public UserDTO create(UserRequestDTO request) {
        List<Role> roles = roleRepository.findAllById(request.roleIds());
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRoles(new java.util.HashSet<>(roles));
        return toDTO(userRepository.save(user));
    }

    @Transactional
    public UserDTO update(Long id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<Role> roles = roleRepository.findAllById(request.roleIds());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRoles(new java.util.HashSet<>(roles));
        return toDTO(user);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getId).toList()
        );
    }
}
