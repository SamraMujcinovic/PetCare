package ba.unsa.etf.nwt.user_service.service;

import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsRequest;
import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsResponse;
import ba.unsa.etf.nwt.system_events_service.actions.grpc.ActionsServiceGrpc;
import ba.unsa.etf.nwt.user_service.security.CustomUserDetailsService;
import ba.unsa.etf.nwt.user_service.security.JwtTokenProvider;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RefreshScope
public class GRPCService {

    @Value("${address: default value}")
    private String address;

    @Value("${port: 0}")
    private int port;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public void save(String actionType, String resourceName, String responseType, String username) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(address, port)
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
                    .setUsername(username)
                    .build());
            channel.shutdown();
            //return response.getStatus();
        }
        catch(Exception e){
            System.out.println("Can't connect to system_events_service to store action!");
            //throw new ResourceNotFoundException("Can't connect to system_events_service to store action!");
        }
    }

    public String getUsernameFromToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7, bearerToken.length());
            if(jwtTokenProvider.validateToken(token)) {
                UserDetails userDetails = customUserDetailsService.loadUserById(jwtTokenProvider.getUserIdFromJWT(token));
                return userDetails.getUsername();
            }
        }
        return "Guest";
    }

}
