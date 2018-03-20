package com.wdx.grpc.service;

import com.wdx.grpc.server.TestInput;
import com.wdx.grpc.server.TestOutPut;
import com.wdx.grpc.server.TestServiceGrpc;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

public class TestService extends TestServiceGrpc.TestServiceImplBase{

    public void sayHello(TestInput request,
                         io.grpc.stub.StreamObserver<TestOutPut> responseObserver) {
        System.out.println("TestService ........param:"+request.getKey());
        TestOutPut testOutPut = TestOutPut.newBuilder().setKey("hello "+request.getKey()).build();
        responseObserver.onNext(testOutPut);
        responseObserver.onCompleted();
    }
}
