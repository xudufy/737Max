package B737Max.Components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class build query string for HTTP get and return the final string
 *
 *
 *  @author xudufy
 *  @version 2.0 2020-05-03
 *  @since 2020-03-01
 */
public class QueryFactory {
    /**
     * Return a query string that can be passed to HTTP URL to request list of airports
     *
     * @param teamName is the name of the team to specify the data copy on server
     * @return the query String of airports which can be appended to URL to form HTTP GET request
     */
    public static String getAirports(String teamName) {
        return "?team=" + teamName + "&action=list&list_type=airports";
    }

    /**
     * Return a query string that can be passed to HTTP URL to request list of airplanes
     *
     * @param teamName is the name of the team to specify the data copy on server
     * @return the query String of airplanes which can be appended to URL to form HTTP GET request
     */
    public static String getAirplanes(String teamName) {
        return "?team="+ teamName + "&action=list&list_type=airplanes";
    }

    /**
     * Return a query string that can be passed to HTTP URL to request list of flight
     *
     * @param teamName is the name of the team to specify the data copy on server
     * @param airport the airport
     * @param day the date
     * @param type type of the airport
     * @return  the query String of flights which can be appended to URL to form HTTP GET request
     */
    private static String getFlights(String teamName, Airport airport, LocalDate day, String type) {
        String airportCode = airport.getCode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        String dayString = day.format(formatter);
        return String.format("?team=%s&action=list&list_type=%s&airport=%s&day=%s",
                teamName, type, airportCode, dayString);
    }

    /**
     * Return a query string that can be passed to HTTP URL to request list of flight.
     * The list of flights should depart from special airport on special day
     * @param teamName is the name of the team to specify the data copy on server
     * @param airport the airport where the flights depart from
     * @param day when the flights depart
     * @return  a query String of flights which can be appended to URL to form HTTP GET request
     */
    public static String getDepartureFlights(String teamName, Airport airport, LocalDate day) {
        return getFlights(teamName, airport, day, "departing");
    }

    /**
     * Return a query string that can be passed to HTTP URL to request list of flight.
     * The list of flights should arrive at a special airport on special day
     * @param teamName is the name of the team to specify the data copy on server
     * @param airport the airport where the flights arrive at
     * @param day when the fights arrive
     * @return a query String of flights
     */
    public static String getArrivalFlights(String teamName, Airport airport, LocalDate day) {
        return getFlights(teamName, airport, day, "arriving");
    }

    /**
     * Return a query string to reset the server database
     * @param teamName is the name of the team to specify the data copy on server
     * @return the String written to HTTP POST to reset server database
     */
    public static String getReset(String teamName) {
        return "?team=" + teamName + "&action=resetDB";
    }

    /**
     * Return a body string to lock the server database
     *
     * @param teamName is the name of the team to acquire the lock
     * @return the String written to HTTP POST to lock server database
     */
    public static String postLock (String teamName) {
        return "team=" + teamName + "&action=lockDB";
    }

    /**
     * Return a body string to unlock the server database
     *
     * @param teamName is the name of the team holding the lock
     * @return the String written to the HTTP POST to unlock server database
     */
    public static String postUnlock (String teamName) {
        return "team=" + teamName + "&action=unlockDB";
    }

    /**
     * Return a body string to return the reservation of trips
     * @param teamName is the name of the team to specify the data copy on server
     * @param trips the reserved trips
     * @return the String written to the HTTP POST to reserve the trips
     */
    public static String postReservation(String teamName, Trip[] trips) {
        String tripXml = XMLInterface.buildReservations(trips);
        return "team="+teamName+"&action=buyTickets&flightData="+tripXml;
    }

}
