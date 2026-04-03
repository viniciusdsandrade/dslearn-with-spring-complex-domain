package com.restful.dslearn.service;

import com.restful.dslearn.dto.RoleDTO;
import com.restful.dslearn.dto.RoleRequestDTO;
import com.restful.dslearn.entity.Role;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.RoleRepository;
import com.restful.dslearn.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.HashSet;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        return toDTO(role);
    }

    @Transactional
    public RoleDTO create(RoleRequestDTO request) {
        Role role = new Role();
        role.setAuthority(request.authority());
        role.setUsers(new HashSet<>(userRepository.findAllById(request.userIds())));
        return toDTO(roleRepository.save(role));
    }

    @Transactional
    public RoleDTO update(Long id, RoleRequestDTO request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        role.setAuthority(request.authority());

        List<User> users = userRepository.findAllById(request.userIds());
        role.setUsers(new HashSet<>(users));
        return toDTO(role);
    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        roleRepository.deleteById(id);
    }

    private RoleDTO toDTO(Role role) {
        List<User> users = role.getUsers() != null
                ? role.getUsers().stream().toList()
                : List.<User>of();
        return new RoleDTO(
                role.getId(),
                role.getAuthority(),
                users.stream().map(User::getId).toList()
        );
    }
}
