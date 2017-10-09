package io.telenor.bustripper;

import java.util.Set;

/**
 * Created by Borja on 09.10.2017.
 */
public class BusTripCallBackWithNumberOfSets extends BusTripsCallBack {
    private Integer numberOfTripSets = null;
    private TripsCallbackWithNumberOfSets listener;
    private boolean last;
    public BusTripCallBackWithNumberOfSets(String url, TripsCallbackWithNumberOfSets callback, boolean last,
                                           int numberOfTripSets) {
        super(url, callback, last);
        this.listener = callback;
        this.last = last;
        this.numberOfTripSets = numberOfTripSets;
    }

    @Override
    public void gotTrips(Set<BusTrip> trips, boolean last){
        listener.gotTrips(trips, numberOfTripSets);
    }
}
