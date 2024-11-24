package util;

import domain.Car;

import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Collects car statistics, including price min, max, average, and standard deviation.
 */
public class CarStatisticsCollector {

    private CarStatisticsCollector() {}

    /**
     * Returns a collector that computes car price statistics.
     *
     * @return A collector for car statistics.
     */
    public static Collector<Car, ?, CarStatistics> toCarStatistics() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                cars -> {
                    Objects.requireNonNull(cars, "The list of cars is empty");

                    double minPrice = cars.stream()
                            .mapToDouble(Car::getPrice)
                            .min()
                            .orElseThrow(() -> new IllegalStateException("Failed to calculate minimum value"));

                    double maxPrice = cars.stream()
                            .mapToDouble(Car::getPrice)
                            .max()
                            .orElseThrow(() -> new IllegalStateException("Failed to calculate maximum value"));

                    double totalSum = cars.stream()
                            .mapToDouble(Car::getPrice)
                            .sum();
                    double averagePrice = totalSum / cars.size();

                    double standardDeviation = Math.sqrt(cars.stream()
                            .mapToDouble(car -> Math.pow(car.getPrice() - averagePrice, 2))
                            .average()
                            .orElse(0.0));

                    return new CarStatistics(
                            minPrice,
                            maxPrice,
                            averagePrice,
                            standardDeviation
                    );
                }
        );
    }
}
