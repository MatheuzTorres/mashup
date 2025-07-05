package br.ufrrj.mashup.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MashupService {

    private final RestTemplate rest = new RestTemplate();

    public double calcularVendaFinal(int caixas, double distancia) {
        String tipo = caixas > 250 ? "carreta" : "caminhao";

        // 1. Chamada REST para preço do tomate (porta 8081)
        double precoTomate = rest.getForObject(
                "http://localhost:8081/api/preco/" + caixas,
                Double.class
        );

        // 2. Chamada GraphQL para frete (porta 8082)
        String query = "{ \"query\": \"{ calcularFrete(distancia: " + distancia + ", tipo: \\\"" + tipo + "\\\") }\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = rest.postForEntity(
                "http://localhost:8082/graphql",
                request,
                String.class
        );

        // 3. Extrair valor de frete do JSON bruto
        String body = response.getBody();
        double frete;
        try {
            String valor = body.split(":")[2].replace("}}", "").trim();
            frete = Double.parseDouble(valor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair valor do frete da resposta: " + body);
        }

        // 4. Cálculo do valor final
        double custo = precoTomate + frete;
        double lucro = custo * 0.55;
        double subtotal = custo + lucro;

        double desconto = 0;
        if (caixas > 300) {
            desconto = subtotal * 0.12;
        } else if (caixas > 50) {
            desconto = subtotal * 0.075;
        }

        double comDesconto = subtotal - desconto;
        double imposto = comDesconto * 0.27;

        return comDesconto + imposto;
    }
}
