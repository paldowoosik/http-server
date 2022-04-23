package com.nhnacademy.httpserver;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nhnacademy.httpserver.vo.GetVo;
import com.nhnacademy.httpserver.vo.IpVo;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            Socket socket = serverSocket.accept();

            byte[] bytes = new byte[2048];
            int numberOfBytes = socket.getInputStream().read(bytes);
            // 요청 헤더
            String request = new String(bytes, 0, numberOfBytes, UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            // 응답 본문
            ObjectNode payload = mapper.createObjectNode();

            String host = socket.getInetAddress().getHostAddress();
            payload.put("origin", host);

            StringBuilder responseHeader = null;
            StringBuilder responseBody = null;

            String query = request.split(lineSeparator())[0].split(" ")[1];
            if (query.equals("/ip")){
                IpVo ipVo = new IpVo(host);
                responseBody = ipVo.ipsResponseBody();
                responseHeader = initResponseHeader(ipVo.ipsResponseBody().length());
            } else if (query.startsWith("/get")) {
                GetVo getVo;
                if (query.startsWith("/get?")) {
                    String[] msgs = query.split("\\?|=|&");
                    getVo = new GetVo(parseMsgs(msgs), host, host+query);
                } else {
                    getVo = new GetVo(host, host+query);
                }
                responseBody = getVo.getsResponseBody();
                responseHeader = initResponseHeader(getVo.getsResponseBody().length());
            } else if (query.equals("/post")) {

            }


            System.out.println("request\n"+request);
            System.out.println("query\n"+query);
            System.out.println("responseHeader\n"+responseHeader);
            System.out.println("responsebody\n"+responseBody);
            try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()))) {
                writer.write(String.valueOf(responseHeader));
                writer.newLine();
                writer.write(String.valueOf(responseBody));

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

//    private static String parseMsgs(String[] msgs) {
//        StringBuilder result = new StringBuilder();
//        result.append(lineSeparator());
//        for (int i = 1; i < msgs.length; i++) {
//            result.append("    \"").append(msgs[i]).append("\": ");
//            if (i != msgs.length-1)
//                result.append(",");
//            result.append(lineSeparator());
//        }
//        return result.toString();
//    }

    static StringBuilder initResponseHeader(int responseBodyLength) {
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append("HTTP/1.1 200 OK").append(lineSeparator())
                         // FIXME: Date 를 yoda time으로 바꾸기
                         .append("Date: " + new Date()).append(lineSeparator())
                         .append("Content-Type: application/json").append(lineSeparator())
                         .append("Content-Length: ").append(responseHeader.length() + responseBodyLength).append(lineSeparator())
                         .append("Server: gunicorn/19.9.0").append(lineSeparator())
                         .append("Access-Control-Allow-Origin: *").append(lineSeparator())
                         .append("Access-Control-Allow-Credentials: true").append(lineSeparator()).append(lineSeparator());
        return responseHeader;
    }
}
