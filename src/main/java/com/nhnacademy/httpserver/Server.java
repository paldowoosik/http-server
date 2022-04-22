package com.nhnacademy.httpserver;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            // 일단 포트번호 80번으로 염
            System.out.println("서버 엶");
            Socket socket = serverSocket.accept();
            System.out.println("연결 완료");
            BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));

//            String data;
//            while ((data = bufferedReader.readLine()) != null) {
//                //client에 데이터 전송
//                BufferedWriter bufferedWriter =
//                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                bufferedWriter.write(data);
//                bufferedWriter.newLine();
////                bufferedWriter.flush();
//
//                System.out.println(data);
//                // 여기가 read가 안끊기고 있음
//            }

            byte[] bytes = new byte[2048];
            int numberOfBytes = socket.getInputStream().read(bytes);
            String request = new String(bytes, 0, numberOfBytes, UTF_8);

            StringBuilder response = new StringBuilder();

            Map<String,String> payload = new HashMap<>();
            String origin = socket.getInetAddress().getHostAddress();
            payload.put("origin", origin);
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);


            response.append("HTTP/1.1 200 OK").append(lineSeparator())
                    .append("Date: " + new Date()).append(lineSeparator())
                    .append("Content-Type: application/json").append(lineSeparator())
                    .append("Content-Length: ").append(json.getBytes(UTF_8).length).append(lineSeparator())
//                    .append("Connection: close").append(lineSeparator())
                    .append("Server: gunicorn/19.9.0").append(lineSeparator())
                    .append("Access-Control-Allow-Origin: *").append(lineSeparator())
                    .append("Access-Control-Allow-Credentials: true").append(lineSeparator()).append(lineSeparator());

            System.out.println(response);
            response.append(json);


            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                writer.write(String.valueOf(response));
                writer.flush();
            }



//            socket.close();
//            bufferedReader.close();
//            bufferedWriter.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
