package service;

import domain.Car;
import util.CarGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Provides services related to car data, including filtering, grouping, and statistics.
 */
public class CarService {

    private final CarGenerator carGenerator;

    /**
     * Default constructor, initializing the car generator.
     */
    public CarService() {
        carGenerator = new CarGenerator();
    }

    /**
     * Filters and gathers cars, excluding a given brand and skipping the first N entries.
     *
     * @param excludeBrand The brand to exclude.
     * @param skipFirstN   The number of entries to skip.
     * @return A list of cars.
     */
    public List<Car> gatherCars(String excludeBrand, int skipFirstN) {
        var excludeCount = new AtomicInteger(0);

        return carGenerator.generateCars()
                .filter(car -> {
                    if (car.getBrand().equals(excludeBrand) && excludeCount.get() < skipFirstN) {
                        excludeCount.incrementAndGet();
                        return false;
                    }
                    return true;
                })
                .limit(500)
                .collect(Collectors.toList());
    }

    /**
     * Groups cars by their class.
     *
     * @param cars A list of cars to group.
     * @return A map grouping cars by their class.
     */
    public Map<String, List<Car>> groupByClass(List<Car> cars) {
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getCarClass));
    }

    /**
     * Filters cars by production date between the specified range.
     *
     * @param cars A list of cars to filter.
     * @param from The start date.
     * @param to   The end date.
     * @return A filtered list of cars.
     */
    public List<Car> filterByTime(List<Car> cars, LocalDate from, LocalDate to) {
        return cars.stream()
                .filter(car ->
                        car.getProductionDate().isAfter(from) &&
                                car.getProductionDate().isBefore(to))
                .toList();
    }

}

