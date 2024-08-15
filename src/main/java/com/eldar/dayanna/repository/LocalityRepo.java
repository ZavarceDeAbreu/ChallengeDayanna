package com.eldar.dayanna.repository;

import com.eldar.dayanna.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalityRepo extends JpaRepository<Locality, Integer> {

    List<Locality> findByPostalCode(Short postalCode);

    @Query(value = "SELECT * FROM locality WHERE name ILIKE :name", nativeQuery = true)
    List<Locality> findByNameLike(@Param("name") String name);

}
