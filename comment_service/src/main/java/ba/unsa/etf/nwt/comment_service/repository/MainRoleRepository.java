package ba.unsa.etf.nwt.comment_service.repository;

import ba.unsa.etf.nwt.comment_service.models.sectionRoles.MainRole;
import ba.unsa.etf.nwt.comment_service.models.sectionRoles.SectionRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainRoleRepository extends JpaRepository<MainRole, Long> {
    MainRole getRoleByName(SectionRoleName sectionRoleName);
}
