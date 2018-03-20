package com.wdx.grpc.server;

import com.wdx.grpc.service.TestService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class RPCServer{

    private int port = 50051;

    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port).addService(new TestService()).build().start();
        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                RPCServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        final RPCServer server = new RPCServer();
        server.start();
        server.blockUntilShutdown();
    }
}
