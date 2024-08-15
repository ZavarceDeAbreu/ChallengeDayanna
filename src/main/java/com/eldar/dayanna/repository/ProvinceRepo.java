package com.eldar.dayanna.repository;

import com.eldar.dayanna.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepo extends JpaRepository<Province, Integer> {

    @Query(value = "SELECT * FROM province WHERE name ILIKE %:name%", nativeQuery = true)
    List<Province> findByName(@Param("name") String name);

    @Query(value = "SELECT * FROM province WHERE code_31662 LIKE %:code31662%", nativeQuery = true)
    List<Province> findByCode31662(@Param("code31662") String code31662);
}
