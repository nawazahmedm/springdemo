package com.javalearnings.securitydemo.repositories.common;

import com.javalearnings.securitydemo.entities.Masterlookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterLookupRepository extends JpaRepository<Masterlookup, Integer> {

    @Query(value = "SELECT *  FROM masterlookup where CodeType = :codeType and SystemFlag = 'Y' and ActiveFlag = 1", nativeQuery = true)
    List<Masterlookup> findByCodeType(String codeType);

}
