package ar.com.zumma.platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.zumma.platform.domain.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

}
