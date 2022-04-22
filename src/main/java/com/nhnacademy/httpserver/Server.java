package com.nhnacademy.httpserver;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            Socket socket = serverSocket.accept();

            byte[] bytes = new byte[2048];
            int numberOfBytes = socket.getInputStream().read(bytes);
            String request = new String(bytes, 0, numberOfBytes, UTF_8);

            ObjectMapper mapper = new ObjectMapper();

            //Map<String, String> payload = new HashMap<>();
            ObjectNode payload = mapper.createObjectNode();

            String origin = socket.getInetAddress().getHostAddress();
            payload.put("origin", origin);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            json += System.lineSeparator();

            StringBuilder response = new StringBuilder();
            response.append("HTTP/1.1 200 OK").append(lineSeparator())
                    // FIXME: Date 를 yoda time으로 바꾸기
                    .append("Date: " + new Date()).append(lineSeparator())
                    .append("Content-Type: application/json").append(lineSeparator())
                    .append("Content-Length: ").append(json.length()).append(lineSeparator())
                    .append("Server: gunicorn/19.9.0").append(lineSeparator())
                    .append("Access-Control-Allow-Origin: *").append(lineSeparator())
                    .append("Access-Control-Allow-Credentials: true").append(lineSeparator());

            // FIXME:
            System.out.println(response);
            response.append(json);

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                writer.write(String.valueOf(response));
                writer.write(lineSeparator());
                writer.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
