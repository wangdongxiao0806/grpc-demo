package com.wdx.grpc.client;

import com.wdx.grpc.server.TestInput;
import com.wdx.grpc.server.TestOutPut;
import com.wdx.grpc.server.TestServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class RPCClient {
    private final ManagedChannel channel;
    private final TestServiceGrpc.TestServiceBlockingStub blockingStub;


    public RPCClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();

        blockingStub = TestServiceGrpc.newBlockingStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public  void greet(String name){
        TestInput request = TestInput.newBuilder().setKey(name).build();
        TestOutPut response = blockingStub.sayHello(request);
        System.out.println(response.getKey());

    }

    public static void main(String[] args) throws InterruptedException {
        RPCClient client = new RPCClient("127.0.0.1",50051);
        for(int i=0;i<5;i++){
            client.greet("world:"+i);
        }

    }
}
