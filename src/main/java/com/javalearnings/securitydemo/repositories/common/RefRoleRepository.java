package com.javalearnings.securitydemo.repositories.common;

import com.javalearnings.securitydemo.entities.common.RefRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefRoleRepository extends JpaRepository<RefRole, Integer> {

    @Query(value = "SELECT * FROM ref_role where active_ind = 'y' ", nativeQuery = true)
    List<RefRole> findByActiveInd();

}
