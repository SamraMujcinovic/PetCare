package ba.unsa.etf.nwt.user_service.repository;

import ba.unsa.etf.nwt.user_service.models.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
