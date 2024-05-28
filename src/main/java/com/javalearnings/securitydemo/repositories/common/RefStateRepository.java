package com.javalearnings.securitydemo.repositories.common;

import com.javalearnings.securitydemo.entities.common.RefState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefStateRepository extends JpaRepository<RefState, Integer> {

    @Query(value = "SELECT *  FROM ref_state where country_id = :countryId", nativeQuery = true)
    List<RefState> findByCountryId(int countryId);

}
