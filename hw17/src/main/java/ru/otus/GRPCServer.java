package ru.otus;


import io.grpc.ServerBuilder;
import java.io.IOException;
import ru.otus.service.SequenceGeneratorService;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var sequenceGeneratorService = new SequenceGeneratorService();

        var server = ServerBuilder
            .forPort(SERVER_PORT)
            .addService(sequenceGeneratorService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
