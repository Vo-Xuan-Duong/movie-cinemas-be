package com.example.movie_cinemas_be.reponsitory;

import com.example.movie_cinemas_be.entitys.Compani;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniRepository extends JpaRepository<Compani,Long> {
}
