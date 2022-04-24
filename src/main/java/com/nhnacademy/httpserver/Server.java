package com.nhnacademy.httpserver;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nhnacademy.httpserver.propertysetter.ContentPropertySetter;
import com.nhnacademy.httpserver.propertysetter.DataPropertySetter;
import com.nhnacademy.httpserver.propertysetter.JsonPropertySetter;
import com.nhnacademy.httpserver.vo.GetVo;
import com.nhnacademy.httpserver.vo.IpVo;
import com.nhnacademy.httpserver.vo.PostMultipartVo;
import com.nhnacademy.httpserver.vo.PostVo;
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

            String origin = socket.getInetAddress().getHostAddress();
            String hostName = socket.getInetAddress().getHostName();
            payload.put("origin", origin);

            StringBuilder responseHeader = new StringBuilder();
            StringBuilder responseBody = new StringBuilder();

            String argsProperty = "";
            String dataProperty = "";
            String jsonProperty = "";
            String contentType = "";

            String query = request.split(lineSeparator())[0].split(" ")[1];

            if (!query.startsWith("/get")) {
                ContentPropertySetter contentPropertySetter = new ContentPropertySetter();
                contentPropertySetter.setProperty(request);
                contentType = contentPropertySetter.getContentProperty();
            }

            if (query.equals("/ip")){
                IpVo ipVo = new IpVo(origin);
                responseBody = ipVo.voResponseBody();
                responseHeader = initResponseHeader(responseBody.length());
            } else if (query.startsWith("/get")) {
                GetVo getVo;
                if (query.startsWith("/get?")) {
                    String[] msgs = query.split("\\?|=|&");
                    getVo = new GetVo(parseMsgs(msgs), hostName, origin, hostName+query);
                } else {
                    getVo = new GetVo("", hostName, origin, hostName+query);
                }
                responseBody = getVo.voResponseBody();
                responseHeader = initResponseHeader(responseBody.length());
            } else if (query.equals("/post") && contentType.equals("aplication/json")) {

                DataPropertySetter dataPropertySetter = new DataPropertySetter();
                dataPropertySetter.setProperty(request);
                dataProperty = dataPropertySetter.getDataProperty();

                JsonPropertySetter jsonPropertySetter = new JsonPropertySetter();
                jsonPropertySetter.setProperty(request);
                jsonProperty = jsonPropertySetter.getJsonProperty();
            }
            if (query.equals("/post") && contentType.startsWith("multipart/form-data")) { // 파일인것
                PostMultipartVo postMultipartVo = new PostMultipartVo(request, origin);
                responseBody.append(postMultipartVo);
                responseHeader = initResponseHeader(responseBody.length());
            }

            // 파일 업로드가 아닌곳
            if (query.startsWith("/post") && !contentType.startsWith("multipart")){ // 기본post
                PostVo postVo = new PostVo(origin, hostName+query, dataProperty, contentType, jsonProperty, hostName);
                responseBody = postVo.voResponseBody();
                responseHeader = initResponseHeader(responseBody.length());
            }

            // 확인을 위한 출력
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

    private static String parseMsgs(String[] msgs) {
        StringBuilder result = new StringBuilder();
        result.append(lineSeparator());
        for (int i = 1; i <= (msgs.length)/2+1; i+=2) {
            result.append("    \"").append(msgs[i]).append("\": ")
                  .append(msgs[i+1]).append("\"");
            if (i < (msgs.length)/2)
                result.append(",");
            result.append(lineSeparator());
        }
        result.append("  ");
        return result.toString();
    }

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
