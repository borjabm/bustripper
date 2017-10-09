package io.telenor.bustripper;

import java.util.Set;

/**
 * Created by Borja on 09.10.2017.
 */
public interface TripsCallbackWithNumberOfSets extends TripsCallback {
    /**
     * Got a list of trips
     * @param trips the set of bus trips found
     * @param numberOfTripSets total number of expected number of trip sets.
     */
    public void gotTrips(Set<BusTrip> trips, int numberOfTripSets);
}
