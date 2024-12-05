package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchByName(@Param("query") String query);


}
