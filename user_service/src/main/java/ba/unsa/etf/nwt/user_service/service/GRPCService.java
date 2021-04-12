package ba.unsa.etf.nwt.user_service.service;

import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsRequest;
import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsResponse;
import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.protobuf.util.Timestamps.fromMillis;
import static java.lang.System.currentTimeMillis;

@Service
public class GRPCService {

    public String save(String actionType, String resourceName, String responseType) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        ActionsServiceGrpc.ActionsServiceBlockingStub stub
                = ActionsServiceGrpc.newBlockingStub(channel);
        ActionsResponse response = stub.save(ActionsRequest.newBuilder()
                .setTimestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                .setMicroservice("user_service")
                .setActionType(actionType)
                .setResourceName(resourceName)
                .setResponseType(responseType)
                .build());
        channel.shutdown();
        return response.getStatus();

    }
}
