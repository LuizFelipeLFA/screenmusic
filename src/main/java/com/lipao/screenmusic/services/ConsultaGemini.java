package com.lipao.screenmusic.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaGemini {

    private static ConverteDados conversor = new ConverteDados();

    public static String obterInformacao(String nomeArtista) {
        String apiKey = "apiKey";
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=" + apiKey;

                HttpClient client = HttpClient.newHttpClient();

        String jsonSaida = """
                {
                                  "contents": [{
                                    "parts":[{"text": "Seja direto: Me dê um breve resumo biográfico curto do artista ou banda: %s"}]
                                  }]
                                }
                                """.formatted(nomeArtista);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSaida))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var dados = conversor.obterDados(response.body(), RespostaGemini.class);
            System.out.println("\n\n");

            return dados.candidates().get(0).content().parts().get(0).text();
        } catch (Exception e) {
            return "Erro ao consultar o Gemini:" + e.getMessage();
        }

    }
}
