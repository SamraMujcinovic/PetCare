package ba.unsa.etf.nwt.user_service.service;

import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsRequest;
import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsResponse;
import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsServiceGrpc;
import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GRPCService {

    public void save(String actionType, String resourceName, String responseType) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                    .usePlaintext()
                    .build();
            ActionsServiceGrpc.ActionsServiceBlockingStub stub
                    = ActionsServiceGrpc.newBlockingStub(channel);
            ActionsResponse response = stub.save(ActionsRequest.newBuilder()
                    .setTimestamp(new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(new Date()))
                    .setMicroservice("user_service")
                    .setActionType(actionType)
                    .setResourceName(resourceName)
                    .setResponseType(responseType)
                    .build());
            channel.shutdown();
            //return response.getStatus();
        }
        catch(Exception e){
            throw new ResourceNotFoundException("Can't connect to system_events_service to store action!");
        }
    }
}
