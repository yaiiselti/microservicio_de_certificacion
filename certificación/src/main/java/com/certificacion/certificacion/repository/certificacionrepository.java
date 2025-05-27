package com.certificacion.certificacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certificacion.certificacion.model.Certificacion;

public interface certificacionrepository extends JpaRepository<Certificacion, Integer> {
    
    List<Certificacion> findByUsuarioid(String usuarioid);


    Optional<Certificacion> findByIdAndUsuarioid(int id, String usuarioid);


    List<Certificacion> findByClaseid(String claseid); // ¡Nuevo método!

    Optional<Certificacion> findByIdAndClaseid(int id, String claseid);
}
