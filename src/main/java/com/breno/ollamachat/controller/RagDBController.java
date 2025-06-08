package com.breno.ollamachat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RagDBController {

    private static final Logger log = LoggerFactory.getLogger(RagDBController.class);

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String DATABASE_SCHEMA = """
        Tabelas disponíveis:
        - clientes (id INT, nome VARCHAR, email VARCHAR, data_cadastro DATE)
        - pedidos (id INT, id_cliente INT, valor_total DECIMAL, data_pedido DATE)

        Relacionamentos:
        - clientes.id = pedidos.id_cliente
        """;

    @GetMapping("/api/loja/pergunte")
    public ResponseEntity<?> askStore(@RequestParam String question) {

        String sqlGenerationPrompt = """
            Com base no seguinte esquema de banco de dados:
            %s

            Gere uma consulta SQL otimizada e correta para a seguinte pergunta:
            %s

            Sua resposta deve conter APENAS a consulta SQL, sem explicações adicionais,
            sem blocos de código (```sql), e sem formatação extra.
            Por exemplo: SELECT nome FROM clientes WHERE id = 1;
            """.formatted(DATABASE_SCHEMA, question);

        log.info("Prompt enviado ao LLM para geração de SQL:\n{}", sqlGenerationPrompt);

        String generatedSql = chatClient
                .prompt()
                .user(sqlGenerationPrompt)
                .call()
                .content();

        // Limpar a string da SQL, caso o LLM adicione aspas ou espaços em branco
        generatedSql = generatedSql.trim().replaceAll("```sql", "").replaceAll("```", "").trim();

        log.info("SQL gerada pelo LLM: {}", generatedSql);

        try {
            // Validação simples para permitir apenas SELECTs por segurança
            if (!generatedSql.toUpperCase().startsWith("SELECT")) {
                throw new IllegalArgumentException("Apenas consultas SELECT são permitidas por segurança.");
            }
            List<Map<String, Object>> results = jdbcTemplate.queryForList(generatedSql);
            log.info("Resultados da consulta: {}", results);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            log.error("Erro ao executar a SQL gerada: {}", e.getMessage());
            // Em um ambiente de produção, você pode retornar um objeto de erro mais amigável
            throw new RuntimeException("Não foi possível executar a consulta. Verifique o log para detalhes. Erro: " + e.getMessage());
        }
    }

}
