package com.certificacion.certificacion.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class Clientservice {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String USUARIO_SERVICE_BASE_URL = "http://8081/api/usuarios/";
    private static final String CLASE_SERVICE_BASE_URL = "http://8082/api/clases/";

    public Map<String, String> getUsuarioDetails(String usuarioId) {
        String url = USUARIO_SERVICE_BASE_URL + usuarioId;
        return restTemplate.getForObject(url, Map.class);
    }

    public Map<String, String> getClaseDetails(String claseId) {
        String url = CLASE_SERVICE_BASE_URL + claseId;
        return restTemplate.getForObject(url, Map.class);
    }
}