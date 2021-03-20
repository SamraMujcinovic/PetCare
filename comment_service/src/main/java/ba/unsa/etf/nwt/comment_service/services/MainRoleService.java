package ba.unsa.etf.nwt.comment_service.services;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.MainRole;
import ba.unsa.etf.nwt.comment_service.repository.MainRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class MainRoleService {
    private final MainRoleRepository mainRoleRepository;

    public MainRoleService(MainRoleRepository mainRoleRepository) {
        this.mainRoleRepository = mainRoleRepository;
    }

    public MainRole addRole(MainRole mainRole) {
        return mainRoleRepository.save(mainRole);
    }

}
