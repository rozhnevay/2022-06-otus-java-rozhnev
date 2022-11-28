package ru.otus;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.SequenceMessage;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private static final AtomicLong sequenceValue = new AtomicLong(0L);

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
            .usePlaintext()
            .build();
        var latch = new CountDownLatch(1);
        var stub = RemoteDBServiceGrpc.newStub(channel);
        stub.generateSequence(Empty.getDefaultInstance(), new StreamObserver<SequenceMessage>() {
            @Override
            public void onNext(SequenceMessage seq) {
                System.out.println("new value:" + seq.getValue());
                sequenceValue.set(seq.getValue());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\nЯ все!");
                latch.countDown();
            }
        });

        var currentValue = 0;
        for (var i = 0; i < 50; i++) {
            var serverValue = sequenceValue.get();
            currentValue += serverValue + 1;
            System.out.println("currentValue:" + currentValue);
            sequenceValue.compareAndSet(serverValue, 0);
            Thread.sleep(500);
        }

        latch.await();

        channel.shutdown();
    }
}
