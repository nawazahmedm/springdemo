package com.javalearnings.securitydemo.repositories.auth;

import com.javalearnings.securitydemo.entities.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "select * from user_info where username = :username",nativeQuery = true)
	Optional<User> findByUsername(String username);

	@Query(value = "select * from user_info where " +
			"((COALESCE(:activeInd, '') = '' ) OR (active_ind = :activeInd)) ",nativeQuery = true)
	Page<User> findByUsernameBasedOnActiveInd(String activeInd, Pageable pageable);

	List<User> findByActiveInd(String activeInd);
}
