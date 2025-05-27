package com.certificacion.certificacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "certificacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certificacion {

    @Id
    @Column(name = "id", nullable = false, length = 50)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "usuario_id", nullable = false, length = 100)
    private String usuarioid;

    @Column(name = "clase_id", nullable = false, length = 100)
    private String claseid;

    @Column(name = "fecha_emision", nullable = false)
    @CreationTimestamp
    private Date fechaemision;

    @Column(name = "url_archivo", length = 500)
    private String urlarchivo;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    
    @Column(name = "metadatos_adicionales", columnDefinition = "TEXT")
    private String metadatosAdicionales;
    
}