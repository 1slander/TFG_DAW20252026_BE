package com.tfgbe.modelo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String URL_BASE =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public String generarSQL(String pregunta) {
        String esquema = "Eres un asistente que genera consultas SQL para PostgreSQL.\n" +
            "Genera SOLO la consulta SQL, sin explicaciones, sin formato markdown, sin bloques de código.\n\n" +
            "Estructura de la base de datos:\n\n" +
            "- users: id_user, first_name, last_name, email, password, is_active, created_at, updated_at\n" +
            "- employees: id_user (FK users), dni, hourly_wage, hire_date, id_role (FK roles), id_restaurant (FK restaurants), id_shift (FK shifts)\n" +
            "- restaurants: id_restaurant, cif, restaurant_name, address, country, phone, capacity, total_tables, id_user (FK employees, owner)\n" +
            "- shifts: id_shift, assign_shift (valores: MORNING, AFTERNOON, EVENING, NIGHT), id_restaurant (FK restaurants)\n" +
            "- tables: id_table, table_number, table_capacity, id_restaurant (FK restaurants), status (valores: BOOKED, NOT_BOOKED, CONFIRMED, PENDING)\n" +
            "- table_assignment: id_assignment, start_time, end_time, id_table (FK tables), id_employee (FK employees)\n" +
            "- roles: id_role, role_name (valores: ROLE_EMPLOYEE, ROLE_TEAM_LEADER, ROLE_ASSISTANT_MANAGER, ROLE_MANAGER, ROLE_OWNER)\n\n" +
            "Reglas importantes:\n" +
            "- Usa sintaxis PostgreSQL\n" +
            "- Para fechas usa NOW()\n" +
            "- employees hereda de users mediante JOIN en id_user\n" +
            "- Para obtener datos completos de un empleado haz JOIN entre employees y users\n\n" +
            "Pregunta: " + pregunta;

        return ejecutarLlamada(esquema);
    }

    public String interpretarResultado(String pregunta, String resultado) {
        String prompt = "Eres un asistente interno de gestión de un restaurante.\n" +
            "Responde en español de forma natural y directa, como un compañero de trabajo.\n" +
            "No uses markdown, asteriscos ni símbolos especiales.\n" +
            "No menciones términos técnicos como base de datos, consulta o registros.\n" +
            "Si el resultado está vacío, indícalo claramente.\n\n" +
            "Equivalencias de valores:\n" +
            "- assign_shift: MORNING=Turno de mañana, AFTERNOON=Turno de tarde, EVENING=Turno de noche, NIGHT=Turno de madrugada\n" +
            "- status de mesa: BOOKED=Reservada, NOT_BOOKED=Libre, CONFIRMED=Confirmada, PENDING=Pendiente\n" +
            "- roles: ROLE_EMPLOYEE=Empleado, ROLE_TEAM_LEADER=Jefe de equipo, ROLE_ASSISTANT_MANAGER=Subgerente, ROLE_MANAGER=Gerente, ROLE_OWNER=Propietario\n\n" +
            "Pregunta del usuario: " + pregunta + "\n" +
            "Datos obtenidos: " + resultado + "\n\n" +
            "Responde de forma concisa y natural.";

        return ejecutarLlamada(prompt);
    }

    private String ejecutarLlamada(String texto) {
        try {
            URL url = new URL(URL_BASE + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String textoEscapado = texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

            String json = "{\"contents\":[{\"parts\":[{\"text\":\"" + textoEscapado + "\"}]}]}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }

            int statusCode = conn.getResponseCode();
            if (statusCode == 429) {
                throw new RuntimeException("LIMITE_TOKENS");
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            // Buscar el texto en la respuesta JSON
            String responseStr = response.toString();
            int textIndex = responseStr.indexOf("\"text\":\"");
            if (textIndex == -1) throw new RuntimeException("No se encontró texto en la respuesta");

            int start = textIndex + 8;
            // Buscar el cierre del texto respetando caracteres escapados
            int end = start;
            while (end < responseStr.length()) {
                if (responseStr.charAt(end) == '"' && responseStr.charAt(end - 1) != '\\') break;
                end++;
            }

            return responseStr.substring(start, end)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error llamando a Gemini: " + e.getMessage());
        }
    }
}