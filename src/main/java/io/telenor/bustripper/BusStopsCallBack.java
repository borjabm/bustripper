package io.telenor.bustripper;

import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.io.IOException;

/**
 *
 */
public class BusStopsCallBack implements InvocationCallback<Response> {

    private ObjectMapper mapper = new ObjectMapper();

    private TripsCallback listener;

    private static int MAXSTOPS = 10;

    public BusStopsCallBack(TripsCallback callback) {
        this.listener = callback;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void completed(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            BusStop[] stops = mapper.readValue(response.readEntity(String.class), BusStop[].class);
            System.out.println(String.format("Got %d busstops nearby", stops.length));
            int numberOfTripSets = (stops.length>MAXSTOPS) ? MAXSTOPS : stops.length;
            for(int i = 0; i< stops.length && i<10;i++) {
                BusStop stop = stops[i];
                boolean isLast = stop == stops[stops.length -1];
                generateFindBusLinesForStopThread(stop, isLast, numberOfTripSets);
            }
        } catch (IOException e) {
            listener.failedGettingTrips(e);
        }

    }

    private void generateFindBusLinesForStopThread(BusStop stop, boolean isLast,  int numberOfTripSets){
        if (listener instanceof TripsCallbackWithNumberOfSets) {
            new Thread(new FindBusLinesForStopWithNumberOfSets(stop.getId(), (TripsCallbackWithNumberOfSets) listener, isLast, numberOfTripSets)).start();
        } else {
            new Thread(new FindBusLinesForStop(stop.getId(), listener, isLast)).start();
        }
    }

    public void failed(Throwable throwable) {
        listener.failedGettingTrips((IOException) throwable);
    }
}
