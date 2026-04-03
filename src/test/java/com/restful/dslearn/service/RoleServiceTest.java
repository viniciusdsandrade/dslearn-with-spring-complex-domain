package com.restful.dslearn.service;

import com.restful.dslearn.dto.RoleDTO;
import com.restful.dslearn.dto.RoleRequestDTO;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void shouldReturnAllRoles() {
        User user = new User();
        user.setId(1L);
        Role role = new Role(1L, "ROLE_ADMIN", Set.of(user));
        when(roleRepository.findAll()).thenReturn(List.of(role));

        List<RoleDTO> result = roleService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).authority()).isEqualTo("ROLE_ADMIN");
        assertThat(result.get(0).userIds()).containsExactly(1L);
    }

    @Test
    void shouldReturnRoleWithNullUsers() {
        Role role = new Role(1L, "ROLE_STUDENT", null);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        RoleDTO result = roleService.findById(1L);

        assertThat(result.userIds()).isEmpty();
    }

    @Test
    void shouldThrowNotFoundWhenRoleMissing() {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> roleService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateRole() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findAllById(List.of(1L))).thenReturn(List.of(user));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> {
            Role saved = inv.getArgument(0);
            saved.setId(5L);
            return saved;
        });

        RoleDTO result = roleService.create(new RoleRequestDTO("ROLE_NEW", List.of(1L)));

        assertThat(result.id()).isEqualTo(5L);
        assertThat(result.authority()).isEqualTo("ROLE_NEW");
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void shouldUpdateRole() {
        User user = new User();
        user.setId(2L);
        Role role = new Role(1L, "ROLE_OLD", new HashSet<>());
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.findAllById(List.of(2L))).thenReturn(List.of(user));

        RoleDTO result = roleService.update(1L, new RoleRequestDTO("ROLE_UPDATED", List.of(2L)));

        assertThat(result.authority()).isEqualTo("ROLE_UPDATED");
    }

    @Test
    void shouldDeleteRole() {
        when(roleRepository.existsById(1L)).thenReturn(true);

        roleService.delete(1L);

        verify(roleRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingRole() {
        when(roleRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> roleService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
