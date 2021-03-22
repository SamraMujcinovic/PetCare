package ba.unsa.etf.nwt.comment_service.services;

import ba.unsa.etf.nwt.comment_service.models.Comment;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.MainRole;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.SectionRoleName;
import ba.unsa.etf.nwt.comment_service.repository.MainRoleRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MainRoleService {
    private final MainRoleRepository mainRoleRepository;

    public MainRoleService(MainRoleRepository mainRoleRepository) {
        this.mainRoleRepository = mainRoleRepository;
    }

    public MainRole addRole(MainRole mainRole) {
        return mainRoleRepository.save(mainRole);
    }

    public MainRole getRoleByName(SectionRoleName sectionRoleName) {
        return mainRoleRepository.findAll()
                .stream()
                .filter(r -> r.getName().equals(sectionRoleName))
                .collect(Collectors.toList()).get(0);
    }

}
