package com.certificacion.certificacion.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.certificacion.certificacion.model.Certificacion;
import com.certificacion.certificacion.repository.certificacionrepository;
import com.certificacion.certificacion.service.Clientservice;
import com.certificacion.certificacion.service.certificadoservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    private final certificadoservice certificadoService;
    private final Clientservice clientService;
    private final certificacionrepository certificacionRepository;
    private final ObjectMapper objectMapper;

    public CertificadoController(certificadoservice certificadoService,
                                 Clientservice clientService,
                                 certificacionrepository certificacionRepository,
                                 ObjectMapper objectMapper) {
        this.certificadoService = certificadoService;
        this.clientService = clientService;
        this.certificacionRepository = certificacionRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/emitir")
    public ResponseEntity<Certificacion> emitirCertificado(
            @RequestParam String usuarioid,
            @RequestParam String claseid) { 

        Map<String, String> usuarioData = clientService.getUsuarioDetails(usuarioid);
        String nombreEstudiante = usuarioData.get("nombre");

        String tituloCurso = certificadoService.getTituloCursoByClaseId(claseid);
        if (tituloCurso == null) {
            throw new RuntimeException("Clase con ID " + claseid + " no encontrada o no tiene un título asociado.");
        }

        Certificacion certificacionExistente = certificadoService.emitirCertificado(usuarioid, claseid);
        Map<String, String> metadatosMap = new HashMap<>();
        metadatosMap.put("nombre_estudiante", nombreEstudiante);
        metadatosMap.put("titulo_curso", tituloCurso);

        try {
            String metadatosJson = objectMapper.writeValueAsString(metadatosMap);
            certificacionExistente.setMetadatosAdicionales(metadatosJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al serializar metadatos adicionales a JSON: " + e.getMessage(), e);
        }
        Certificacion certificadoFinal = certificacionRepository.save(certificacionExistente);
        return ResponseEntity.ok(certificadoFinal);
    }

     @GetMapping("/buscar/{id}")
    public ResponseEntity<Certificacion> obtenerCertificadoPorId(@PathVariable Integer id) {
        Certificacion certificacion = certificadoService.obtenerCertificacionPorId(id);
        if (certificacion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificado con ID " + id + " no encontrado.");
        }
        return ResponseEntity.ok(certificacion);
    }
}