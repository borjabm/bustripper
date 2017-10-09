package io.telenor.bustripper;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

/**
 * Searches for bus stops in area provided.
 */
public class FindBusStop implements Runnable {

    private String searchTerm;

    private Client client;

    private TripsCallback listener;

    public FindBusStop(TripsCallback callback, String searchTerm) {
        this.listener = callback;
        this.searchTerm = searchTerm;
    }

    public void run() {
        try {
            RuterBusStopURI busStopURI = new RuterBusStopURI(searchTerm);
            String targetURI = busStopURI.toStringURI();
            ClientConfig configuration = new ClientConfig();

            client = ClientBuilder.newClient(configuration);

            Invocation.Builder invocationBuilder = client
                    .target(targetURI)
                    .request(MediaType.APPLICATION_JSON);

            final AsyncInvoker asyncInvoker = invocationBuilder.async();
            BusStopsCallBack callback = new BusStopsCallBack(listener);
            asyncInvoker.get(callback);
        }catch (URISyntaxException e) {
            System.out.println("Failed uri. " + e.getMessage());
        }

    }
}
