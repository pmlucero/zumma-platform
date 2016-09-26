package ar.com.zumma.platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.zumma.platform.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);
}

