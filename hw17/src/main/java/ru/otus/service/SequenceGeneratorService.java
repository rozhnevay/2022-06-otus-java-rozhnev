package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.SequenceMessage;

public class SequenceGeneratorService extends RemoteDBServiceGrpc.RemoteDBServiceImplBase {


    @Override
    public void generateSequence(Empty request, StreamObserver<SequenceMessage> responseObserver) {

        for (var i = 0; i < 31; i++) {
            responseObserver.onNext(SequenceMessage.newBuilder().setValue(i).build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();
    }

}
