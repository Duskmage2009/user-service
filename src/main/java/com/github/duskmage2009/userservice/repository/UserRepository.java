package com.github.duskmage2009.userservice.repository;

import com.github.duskmage2009.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * from users where birth_date  between :from AND :to", nativeQuery = true)
    List<User> findAllByBirthDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

}
