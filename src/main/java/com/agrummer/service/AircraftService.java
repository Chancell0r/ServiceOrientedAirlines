package com.agrummer.service;

import com.agrummer.entity.Aircraft;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Simple service to provide information about aircraft operated by Service Oriented Airlines.
 *
 * There is no need to modify this class for the purpose of the coding exercise!
 */
public class AircraftService {

    private Collection<Aircraft> availableAircraft;

    public AircraftService(Collection<Aircraft> availableAircraft) {
        this.availableAircraft = availableAircraft;
    }

    public static AircraftService init() {
        return new AircraftService(Arrays.asList(
                new Aircraft("Cessna Citation M2", 6, 12, 400, 0.5),
                new Aircraft("Gulfstream G200", 18, 36, 2000, 0.8),
                new Aircraft("Embraer Brasilia", 14, 28, 2400, 0.65),
                new Aircraft("Beechcraft 1900D", 18, 36, 3800, 1.0),
                new Aircraft("Bombardier CRJ900", 36, 72, 8800, 3.47)
        ));
    }

    /**
     * Optionally returns the smallest available aircraft that meets all of the minimum requirements provided (if one exists)
     *
     * @param minSeatingCapacity    minimum number of seats for passengers
     * @param minCheckedBagCapacity minimum capacity for checked bags
     * @param minRangeKm            minimum range in kilometers
     * @return optional of Aircraft
     * @see Aircraft
     */
    public Optional<Aircraft> getAircraftForLoad(int minSeatingCapacity, int minCheckedBagCapacity, double minRangeKm) {
        List<Aircraft> meetsRequirements = availableAircraft.stream().filter(aircraft -> {
            return aircraft.getSeatingCapacity() >= minSeatingCapacity
                    && aircraft.getCheckedBagCapacity() >= minCheckedBagCapacity
                    && getMaxRange(aircraft) >= minRangeKm;
        }).sorted(Comparator.comparingInt(Aircraft::getSeatingCapacity)).collect(Collectors.toList());
        if (meetsRequirements.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(meetsRequirements.get(0));
        }
    }

    /**
     * Returns the greatest passenger seating capacity of all currently available aircraft
     *
     * @return number of seats
     */
    public int getMaximumSeatingCapacity() {
       Optional<Aircraft> aircraft = availableAircraft.stream().max(Comparator.comparingInt(Aircraft::getSeatingCapacity));
        return aircraft.map(Aircraft::getSeatingCapacity).orElse(0);
    }

    /**
     * Returns the greatest checked bag capacity of all currently available aircraft
     *
     * @return number of checked bags
     */
    public int getMaximumCheckedBagCapacity() {
        Optional<Aircraft> aircraft = availableAircraft.stream().max(Comparator.comparingInt(Aircraft::getCheckedBagCapacity));
        return aircraft.map(Aircraft::getCheckedBagCapacity).orElse(0);
    }

    private static double getMaxRange(Aircraft aircraft) {
        return aircraft.getMaxFuelCapacityKg() / aircraft.getFuelBurnRateKgKm();
    }

}
