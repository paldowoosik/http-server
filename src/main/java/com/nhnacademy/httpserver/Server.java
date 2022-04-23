package com.nhnacademy.httpserver;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nhnacademy.httpserver.vo.PostVo;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class Server {
    public static void main(String[] args) {
        // Server: 192.168.71.79
        // Client: 192.168.71.40
        // http://192.168.71.79/get
        // backlog- 들어오는 연결 대기열의 요청된 최대 길이입니다.
        try (ServerSocket serverSocket = new ServerSocket(80, 50,
            InetAddress.getByName("192.168.0.8"))) {
            Socket socket = serverSocket.accept();

            byte[] bytes = new byte[2048];
            int numberOfBytes = socket.getInputStream().read(bytes);
            // 요청 헤더
            String request = new String(bytes, 0, numberOfBytes, UTF_8);

            ObjectMapper mapper = new ObjectMapper();

            PostVo postVo = new PostVo(239, "192.168.0.8", "http://test-vm.com/post");
            System.out.println(postVo);

            // 응답 본문
            ObjectNode payload = mapper.createObjectNode();

            String origin = socket.getInetAddress().getHostAddress();
            payload.put("origin", origin);

            //String query = request.split(lineSeparator())[0].split(" ")[1];

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            json += System.lineSeparator();

            StringBuilder responseHeader = new StringBuilder();

            String query = request.split(lineSeparator())[0].split(" ")[1];
            String s = "";
            StringBuilder responseBody = new StringBuilder();
            if (query.startsWith("/get?")) {
                String[] strings = query.split("\\?")[1].split("&");
                for (int i = 0; i < strings.length; i++) {
                    String[] keyAndValue = strings[i].split("=");
                    s += "  \"" + keyAndValue[0] + "\": " + "\"" + keyAndValue[1] + "\",\r\n";

                }
                s += "  ";
            }

            responseBody.append("{").append(lineSeparator()).append("   \"args\": {");
            if (query.contains("?")) {
                responseBody.append(lineSeparator()).append(s);  //여기에 메시지
            }
            responseBody.append("},").append(lineSeparator())
                                     .append("   \"headers\": {").append(lineSeparator())
                                     .append("      \"Accept\": \"*/*\",").append(lineSeparator())
                                     .append("      \"Host\": \"" + socket.getInetAddress().getHostAddress() + "\",").append(lineSeparator())
                                     .append("      \"User-Agent\": \"curl/7.64.1\"").append(lineSeparator())
                                     .append("   },").append(lineSeparator())
                                     .append("   \"origin\": \"").append(origin).append(",").append(lineSeparator())
                                     .append("   \"url\": \"").append(request.split("\r\n")[1].split(" ")[1]).append("\"").append(lineSeparator())
                                     .append("}").append(lineSeparator());

            // 응답 헤더
            responseHeader.append("HTTP/1.1 200 OK").append(lineSeparator())
                          // FIXME: Date 를 yoda time으로 바꾸기
                          .append("Date: " + new Date()).append(lineSeparator())
                          .append("Content-Type: application/json").append(lineSeparator())
//                    .append("Content-Length: ").append(json.length()).append(lineSeparator())
                          .append("Content-Length: ").append(responseHeader.length() + responseBody.length()).append(lineSeparator())
                          .append("Server: gunicorn/19.9.0").append(lineSeparator())
                          .append("Access-Control-Allow-Origin: *").append(lineSeparator())
                          .append("Access-Control-Allow-Credentials: true").append(lineSeparator()).append(lineSeparator());

            // curl 요청이 GET 으로 왔을 때
            if (request.split(lineSeparator())[0].equals("/ip")) {
                responseBody.append(json);
            }

            System.out.print(request);
            System.out.println(responseHeader);

            try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()))) {
                writer.write(String.valueOf(responseHeader));
                writer.newLine();
                writer.write(String.valueOf(responseBody));

//                writer.write(String.valueOf(new GetVo().toString()));
                writer.write(lineSeparator());
                writer.flush();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
