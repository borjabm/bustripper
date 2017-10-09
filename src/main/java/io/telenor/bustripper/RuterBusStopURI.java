package io.telenor.bustripper;

import java.net.URI;
import java.net.URISyntaxException;

public class RuterBusStopURI {
    private static final String SEARCH_URL = "http://reisapi.ruter.no/Place/GetPlaces/";

    private String searchTerm;
    public RuterBusStopURI(String searchTerm){
        this.searchTerm = searchTerm.replaceAll("[^a-zA-Z\\s]", "");
    }

    public String toStringURI() throws URISyntaxException {
        URI uri = new URI(
                "http",
                "reisapi.ruter.no",
                "/Place/GetPlaces/"+searchTerm,
                null);
        return uri.toASCIIString();
    }
}
