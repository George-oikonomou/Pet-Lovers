package pet.lovers.service;

import org.springframework.stereotype.Service;
import pet.lovers.entities.Role;
import pet.lovers.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role updateOrInsert(Role role) {
        return roleRepository.findByName(role.getName()).orElseGet(() -> roleRepository.save(role));
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }
}
