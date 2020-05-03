package B737Max.Components;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * This class holds values pertaining to a single Flight. Class member attributes
 * are from Class Airport, Airplane and the same as defined by the CS509 server API
 *
 *
 *  @author xudufy
 *  @version 2.0 2020-05-03
 *  @since 2020-03-01
 */
public class Flight {
    private ZonedDateTime departureTime, arrivalTime;
    private Duration travelTime;
    private Airport departureAirport, arrivalAirport;
    private String flightNo;
    private Airplane airplane;
    private int numFirstRemained, numCoachRemained;
    private BigDecimal firstPrice, coachPrice;

    /**
     * get a string of basic flight information
     * @return
     */
    @Override
    public String toString() {
        return "Flight{" +
                "departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", travelTime=" + travelTime +
                ", departureAirport=" + departureAirport.getCode() +
                ", arrivalAirport=" + arrivalAirport.getCode() +
                ", flightNo='" + flightNo + '\'' +
                ", airplane=" + airplane.getModel() +
                ", numFirstRemained=" + numFirstRemained +
                ", numCoachRemained=" + numCoachRemained +
                ", firstPrice=" + firstPrice +
                ", coachPrice=" + coachPrice +
                '}';
    }

    /**
     * @return
     */
    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * @return
     */
    public ZonedDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @return
     */
    public Duration getTravelTime() {
        return travelTime;
    }

    /**
     * @return
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * @return
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * @return
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * @return
     */
    public Airplane getAirplane() {
        return airplane;
    }

    /**
     * @return
     */
    public int getNumFirstRemained() {
        return numFirstRemained;
    }

    /**
     * @return
     */
    public int getNumCoachRemained() {
        return numCoachRemained;
    }

    /**
     * @return
     */
    public BigDecimal getFirstPrice() {
        return firstPrice;
    }

    /**
     * @return
     */
    public BigDecimal getCoachPrice() {
        return coachPrice;
    }

    /**
     *
     * @param departureTime
     * @param arrivalTime
     * @param travelTime
     * @param departureAirport
     * @param arrivalAirport
     * @param flightNo
     * @param airplane
     * @param numFirstRemained
     * @param numCoachRemained
     * @param firstPrice
     * @param coachPrice
     * @throws IllegalArgumentException
     */
    public Flight(ZonedDateTime departureTime, ZonedDateTime arrivalTime, Duration travelTime, Airport departureAirport,
                  Airport arrivalAirport, String flightNo, Airplane airplane, int numFirstRemained,
                  int numCoachRemained, BigDecimal firstPrice, BigDecimal coachPrice) throws IllegalArgumentException {
        this.departureTime = departureTime.withZoneSameInstant(departureAirport.getTimeZone());
        this.arrivalTime = arrivalTime.withZoneSameInstant(arrivalAirport.getTimeZone());
        this.travelTime = travelTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.flightNo = flightNo;
        this.airplane = airplane;
        this.numFirstRemained = numFirstRemained;
        this.numCoachRemained = numCoachRemained;
        this.firstPrice = firstPrice;
        this.coachPrice = coachPrice;
        if (this.numFirstRemained<=0 && this.numCoachRemained<=0) {
            throw new IllegalArgumentException("No Seats available on this flight.");
        }
    }

    /**
     * @return
     */
    public boolean checkAvailable() {
        return numFirstRemained>0 || numCoachRemained>0;
    }

    /**
     * Is there available seats
     * @param seat
     * @return
     */
    public SeatClass checkSeatClass(SeatClass seat) {
        if (numFirstRemained>0 && numCoachRemained>0) {
            return seat;
        }

        if (numFirstRemained>0) {
            return SeatClass.FIRST;
        }

        return SeatClass.COACH;
    }

    /**
     * Return the single price of available Seat type
     * @param seat
     * @return
     */
    public BigDecimal getPrice(SeatClass seat) {
        if (seat==SeatClass.FIRST) {
            return firstPrice;
        }
        return coachPrice;
    }
}
