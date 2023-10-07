package model.Client;

import model.DictionaryException;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    // IP and port
    private String ipAddress;
    private int port;
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;

    public LinkedBlockingQueue<String> requestsQueue = new LinkedBlockingQueue<>(1);

    public Client(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void startConnection() {
        try
        {
            this.clientSocket = new Socket(this.ipAddress, this.port);

            // Output and Input Stream
            this.out = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            String sendData ="I want to connect";

            this.out.write(sendData + "\n");
            System.out.println("Data sent to Server--> " + sendData);
            this.out.flush();

            String message;
            if ((message = this.in.readLine()) != null){
                System.out.println(message);
            } else {
                System.out.println("Connection fail at the start");
            }
        } catch (IOException e) {
            System.out.println("Connection failed!");
        }
    }

    public String sendRequest(String request) {
        String respond = null;
        try {
            // send the data of users operations to sever
            this.out.write(request + "\n");
            this.out.flush();
            if ((respond = this.in.readLine()) != null){
                System.out.println(respond);
            } else {
                throw new DictionaryException("sendMessage failed!");
            }
        } catch(IOException e) {
            System.out.println("sendMessage failed!");
        } catch(DictionaryException e) {
            System.out.println(e.getMessage());
        }
        return respond;
    }

    public void stopConnection() {
        try {
            this.in.close();
            this.out.close();
            this.clientSocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
