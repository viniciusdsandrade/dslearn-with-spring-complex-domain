package com.restful.dslearn.service;

import com.restful.dslearn.dto.UserDTO;
import com.restful.dslearn.dto.UserRequestDTO;
import com.restful.dslearn.entity.Role;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.RoleRepository;
import com.restful.dslearn.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@test.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> result = userService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Alice");
    }

    @Test
    void shouldFindUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@test.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.findById(1L);

        assertThat(result.email()).isEqualTo("alice@test.com");
    }

    @Test
    void shouldThrowNotFoundWhenUserMissing() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateUser() {
        Role role = new Role();
        role.setId(1L);
        when(roleRepository.findAllById(List.of(1L))).thenReturn(List.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        UserDTO result = userService.create(
                new UserRequestDTO("Bob", "bob@test.com", "pass123", List.of(1L)));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.name()).isEqualTo("Bob");
        assertThat(result.roleIds()).containsExactly(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldUpdateUser() {
        Role role = new Role();
        role.setId(2L);
        User user = new User();
        user.setId(1L);
        user.setName("Old");
        user.setEmail("old@test.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findAllById(List.of(2L))).thenReturn(List.of(role));

        UserDTO result = userService.update(1L,
                new UserRequestDTO("New", "new@test.com", "newpass", List.of(2L)));

        assertThat(result.name()).isEqualTo("New");
        assertThat(result.email()).isEqualTo("new@test.com");
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingUser() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> userService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
