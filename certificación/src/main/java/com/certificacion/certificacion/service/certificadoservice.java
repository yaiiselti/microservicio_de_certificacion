package com.certificacion.certificacion.service;

import java.util.List;
import java.util.Optional;



import com.certificacion.certificacion.model.Certificacion;


import com.certificacion.certificacion.repository.certificacionrepository;

import org.springframework.stereotype.Service;

@Service
public class certificadoservice {

    private final certificacionrepository certificacionRepository;

    public certificadoservice(certificacionrepository certificacionRepository) {
        this.certificacionRepository = certificacionRepository;
    }

    public Certificacion emitirCertificado(String usuarioid, String claseid) {
        Certificacion nuevaCertificacion = new Certificacion();
        nuevaCertificacion.setUsuarioid(usuarioid);
        nuevaCertificacion.setClaseid(claseid);
        nuevaCertificacion.setEstado("EMITIDO");
     

        nuevaCertificacion.setMetadatosAdicionales(null); 
        nuevaCertificacion.setUrlarchivo(null);
        return certificacionRepository.save(nuevaCertificacion);
    }

    public Certificacion obtenerCertificacionPorId(Integer id) {
        return certificacionRepository.findById(id).orElse(null);
    }

    public Optional<Certificacion> obtenerCertificadoPorIdYUsuario(int id, String usuarioid) {
        return certificacionRepository.findByIdAndUsuarioid(id, usuarioid);
    }

    public List<Certificacion> obtenerCertificadosPorUsuario(String usuarioid) {
        return certificacionRepository.findByUsuarioid(usuarioid);
    }

    public List<Certificacion> obtenerCertificadosPorClase(String claseid) {
        return certificacionRepository.findByClaseid(claseid);
    }

    public Certificacion actualizarEstadoCertificado(int id, String nuevoEstado) {
        Optional<Certificacion> actcer = certificacionRepository.findById(id);

        if (actcer.isPresent()) {
            Certificacion certificado = actcer.get();
            certificado.setEstado(nuevoEstado);
            return certificacionRepository.save(certificado);
        } else {
            throw new RuntimeException("Certificado con ID " + id + " no encontrado para actualizar estado.");
        }
    }

    public void eliminarCertificado(int id) {
        if (!certificacionRepository.existsById(id)) {
            throw new RuntimeException("Certificado con ID " + id + " no existe para eliminar.");
        }
        certificacionRepository.deleteById(id);
    }
}