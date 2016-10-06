package ar.com.zumma.platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.zumma.platform.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);

}
