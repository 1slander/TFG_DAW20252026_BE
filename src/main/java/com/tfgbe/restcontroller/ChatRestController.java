package com.tfgbe.restcontroller;

import com.tfgbe.modelo.dto.ChatRequestDto;
import com.tfgbe.modelo.dto.ChatResponseDto;
import com.tfgbe.modelo.services.GeminiService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

    @Autowired
    private GeminiService geminiService;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    public ResponseEntity<ChatResponseDto> chat(@RequestBody ChatRequestDto request) {
        try {
            String pregunta = request.getPregunta();

            // Paso 1: Generar SQL
            String sql = geminiService.generarSQL(pregunta);
            sql = sql.trim()
                     .replace("```sql", "")
                     .replace("```", "")
                     .trim();

            // Paso 2: Ejecutar SQL
            String resultado = ejecutarSQL(sql);

            // Paso 3: Interpretar resultado
            String respuesta = geminiService.interpretarResultado(pregunta, resultado);
            respuesta = respuesta
                .replace("**", "")
                .replace("* ", "• ")
                .replace("`", "");

            return ResponseEntity.ok(new ChatResponseDto(respuesta));

        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().equals("LIMITE_TOKENS")) {
                return ResponseEntity.ok(new ChatResponseDto(
                    "El asistente ha alcanzado el límite de consultas. Inténtalo más tarde."
                ));
            }
            return ResponseEntity.ok(new ChatResponseDto(
                "Ha ocurrido un error al procesar tu consulta. Inténtalo de nuevo."
            ));
        }
    }

    private String ejecutarSQL(String sql) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            List results = query.getResultList();

            if (results.isEmpty()) return "No se encontraron resultados.";

            StringBuilder resultado = new StringBuilder();
            for (Object row : results) {
                if (row instanceof Object[]) {
                    for (Object col : (Object[]) row) {
                        resultado.append(col != null ? col.toString() : "null").append("  ");
                    }
                } else {
                    resultado.append(row != null ? row.toString() : "null");
                }
                resultado.append("\n");
            }
            return resultado.toString();

        } catch (Exception e) {
            return "Error ejecutando la consulta: " + e.getMessage();
        }
    }
}