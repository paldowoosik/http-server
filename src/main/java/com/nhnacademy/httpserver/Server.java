package com.nhnacademy.httpserver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args ) {
        try {
            String data;
            // 일단 포트번호 10000번으로 염
            ServerSocket serverSocket = new ServerSocket(80);

            Socket socket = serverSocket.accept();

            BufferedReader bufferedReader =
                new BufferedReader( new InputStreamReader(socket.getInputStream()));

            while((data = bufferedReader.readLine()) != null){
                System.out.println(data);
                // 여기가 read가 안끊기고 있음
            }

//            //client에 데이터 전송
//            BufferedWriter bufferedWriter =
//                new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
//            bufferedWriter.write("hello world");
//            bufferedWriter.newLine();
//            bufferedWriter.flush();

            socket.close();
            serverSocket.close();
            bufferedReader.close();
//            bufferedWriter.close();
        }

        catch( Exception e ){
            e.printStackTrace();
        }
    }
}