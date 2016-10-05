package ar.com.zumma.platform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.zumma.platform.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);
    
    @Query("select u from User u where u.email like concat(:filter,'%') or role like concat(:filter,'%')")
    public List<User> find(@Param("filter") String filter);

    @Query("select u from User u where u.email like concat(:filter,'%') or role like concat(:filter,'%')")
    public Page<User> find(@Param("filter") String filter, Pageable pageable);
    
}

