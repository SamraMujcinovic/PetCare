package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.roles.Role;
import ba.unsa.etf.nwt.user_service.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }
}
