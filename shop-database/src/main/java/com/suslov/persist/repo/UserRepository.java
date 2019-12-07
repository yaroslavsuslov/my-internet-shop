package com.suslov.persist.repo;

import com.suslov.persist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> getUserByUsername(String username);

    @Query("select distinct u " +
            "from User u " +
            "left join fetch u.roles r " +
            "order by u.id")
    List<User> getAllUsersWithRoles();
}