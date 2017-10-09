package io.telenor.bustripper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Callback from Jersey when bustrips are there.
 */
public class BusTripsCallBack implements InvocationCallback<Response> {
    ObjectMapper mapper = new ObjectMapper();
    String url;
    private TripsCallback listener;
    private boolean last;

    public BusTripsCallBack(String url, TripsCallback callback, boolean last) {
        this.url = url;
        this.listener = callback;
        this.last = last;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void completed(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        String content = response.readEntity(String.class);

        try {
            if (content!=null && !content.isEmpty()) {
                BusTrip[] trips = mapper.readValue(content, BusTrip[].class);
                HashSet<BusTrip> set = new HashSet<>(Arrays.asList(trips));
                if(!set.isEmpty()) {
                    gotTrips(set, last);
                } else {
                    gotTrips(new HashSet<>(), last);
                }
            } else {
                gotTrips(new HashSet<>(), last);
            }
        } catch (IOException e) {
            if (last) {
                listener.failedGettingTrips(e);
            }
        }

    }

    public void gotTrips(Set<BusTrip> trips, boolean last){
        listener.gotTrips(trips, last);
    }

    public void failed(Throwable throwable) {
        listener.failedGettingTrips((IOException) throwable);
    }
}
