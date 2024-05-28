package com.javalearnings.securitydemo.repositories.common;

import com.javalearnings.securitydemo.entities.common.RefCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefCountryRepository extends JpaRepository<RefCountry, Integer> {

}
