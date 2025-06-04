package com.certificacion.certificacion.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Clientservice {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String USUARIO_SERVICE_URL = "http://localhost:8085/Usuarios/";
    public Map<String, String> getUsuarioDetails(String usuarioId) {
        String url = USUARIO_SERVICE_URL + usuarioId;
        return restTemplate.getForObject(url, Map.class);
    }

}