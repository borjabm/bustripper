package io.telenor.bustripper;

import javax.ws.rs.client.AsyncInvoker;

/**
 * Created by Borja on 09.10.2017.
 */
public class FindBusLinesForStopWithNumberOfSets extends FindBusLinesForStop {
    private int numberOfTripSets;
    private TripsCallbackWithNumberOfSets listener;
    private boolean last;

    public FindBusLinesForStopWithNumberOfSets(String stopId, TripsCallbackWithNumberOfSets callback, boolean last, int numberOfTripSets) {
        super(stopId, callback, last);
        this.listener = callback;
        this.last = last;
        this.numberOfTripSets = numberOfTripSets;
    }

    @Override
    public void invokeAndSendResponseToCallback(AsyncInvoker asyncInvoker, String target, int numberOfTripSets) {
        asyncInvoker.get(new BusTripCallBackWithNumberOfSets(target, listener, last, this.numberOfTripSets));
    }

}
