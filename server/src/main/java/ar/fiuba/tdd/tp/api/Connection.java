package ar.fiuba.tdd.tp.api;

import ar.fiuba.tdd.tp.motor.games.Engine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class Connection extends Thread {
    private final ServerSocket serverSocket;
    private final Engine engine;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Request request;
    private Response response;

    public Connection(ServerSocket serverSocket, Engine engine) {
        this.serverSocket = requireNonNull(serverSocket);
        this.engine = engine;
    }

    public void closeConnection() {
        try {
            if (nonNull(clientSocket) && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getStream(Socket clientSocket) throws IOException {
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void speak() throws IOException, ClassNotFoundException {
        boolean exit = false;
        while (!exit && nonNull(request = (Request) inputStream.readObject())) {
            if (request.isExit()) {
                exit = true;
                response = new Response("exit");
            } else {
                response = new Response(engine.request(request.getSomething()));
            }
            outputStream.writeObject(response);
            outputStream.flush();
        }
    }

    public void run() {
        try {
            while (!serverSocket.isClosed() && nonNull(clientSocket = serverSocket.accept())) {
                ServerOutput.clientConnected(serverSocket.getLocalPort());
                getStream(clientSocket);
                speak();
                clientSocket.close();
                ServerOutput.clientDisconnected(serverSocket.getLocalPort());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}