package com.lipao.screenmusic.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RespostaGemini(List<Candidato> candidates) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Candidato(Conteudo content) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Conteudo(List<Parte> parts) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Parte(String text) {}
