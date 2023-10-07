package model.Server;

//import model.Client.ClientApplication;
import model.DictionaryException;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Declare the port number
    int port;
    private String ipAddress;
    private ServerSocket serverSocket;

    // Identifies the user number connected
    private static int counter = 0;

    public Server(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }


    // Listen the requests from client and connect
    public void connect() {
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try {
            this.serverSocket = factory.createServerSocket(this.port);
            System.out.println("Waiting for client connection-");

            // Wait for connections.
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Connection fail!");
        }
        this.stop();
    }

    public void stop() {
        try{
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("socket close fail");
        }

    }

}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void addWord(String word, String meanings) {
        try {
            Dictionary d = new Dictionary();
            if (d.isWordExist(word)) {
                this.out.write("wordExist\n");
                this.out.flush();
            } else {
                if (d.addWord(word, meanings)) {
                    this.out.write("addSuccess\n");
                    this.out.flush();
                } else {
                    this.out.write("addFail\n");
                    this.out.flush();
                }
            }

            this.out.flush();//clear the stream of any element
        } catch (IOException e) {
            System.out.println("Add fail");
        }

    }

    public void deleteWord(String word) {
        try {
            Dictionary d = new Dictionary();
            if (!(d.isWordExist(word))) {
                this.out.write("wordNotExist\n");
                this.out.flush();
            } else {
                if (d.deleteWord(word)) {
                    this.out.write("deleteSuccess\n");
                    this.out.flush();
                } else {
                    this.out.write("deleteFail\n");
                    this.out.flush();
                }
            }
            this.out.flush();//clear the stream of any element
        } catch (Exception e) {
            System.out.println("Delete fail");
        }
    }

    public void queryWord(String word) {
        try {
            Dictionary d = new Dictionary();
            if (!d.isWordExist(word)) {
                this.out.write("wordNotExist\n");
                this.out.flush();
            } else {
                String res = d.searchWord(word);
                if (res == null) {
                    this.out.write("QueryFail\n");
                    this.out.flush();
                } else {
                    this.out.write(res + "\n");
                    this.out.flush();
                }
            }
            this.out.flush();//clear the stream of any element
        } catch (IOException e) {
            System.out.println("Query fail");
        } catch (DictionaryException e) {
            System.out.println("Query fail");
        }
    }

    @Override
    public void run() {
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String requestLine = null;
            while ((requestLine = this.in.readLine()) != null) {
                System.out.println(requestLine);
                String[] requestArray = requestLine.split("@");

                String command;
                String word;
                String meanings = null;

                if (!requestLine.contains("@")) {
                    System.out.println("CLIENT: " + requestLine);
                    this.out.write("Server: Hi Client!!!\n");
                    this.out.flush();
                } else if(requestArray.length < 2) {
                    this.out.write("Empty word\n");
                    this.out.flush();
                    throw new DictionaryException("Empty word");
                } else {
                    command = requestArray[0];
                    word = requestArray[1];

                    switch (command) {
                        case "add":
                            if (requestArray.length < 2) {
                                this.out.write("Wrong command\n");
                                this.out.flush();
                                throw new DictionaryException("Wrong command");
                            } else if (requestArray.length < 3 || requestArray[2] == null) {
                                this.out.write("Empty meanings\n");
                                this.out.flush();
                                throw new DictionaryException("Empty meanings");
                            } else {
                                meanings = requestArray[2];
                                this.addWord(word, meanings);
                            }
                            break;
                        case "delete":
                            this.deleteWord(word);
                            break;
                        case "query":
                            this.queryWord(word);
                            break;
                        default:
                            break;
                    }
                }

            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Connection fail");
        } catch (DictionaryException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Main {
    public static void main(String[] args)
    {
//        args = new String[2];
//        args[0] = "localhost";
//        args[1] = "3005";
        if (args.length == 2) {
            Server server = new Server(args[0], Integer.valueOf(args[1]));
            server.connect();
            if (server.port < 1024 || server.port > 65535) {
                System.out.println("Invaild input");
                System.exit(1);
            }
        } else {
            System.out.println("Invaild input");
        }
    }
}

