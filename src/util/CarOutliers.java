package util;

import domain.Car;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Analyzes outliers in a list of cars based on price using the IQR method.
 */
public class CarOutliers {

    private CarOutliers() {}

    /**
     * Analyzes car prices for outliers.
     *
     * @param cars A list of cars to analyze.
     * @return A map with the count of outliers and normal data.
     */
    public static Map<String, Integer> analyzeOutliers(List<Car> cars) {
        Objects.requireNonNull(cars, "The list of cars is empty");

        var prices = cars.stream()
                .map(Car::getPrice)
                .sorted()
                .toList();

        var q1 = findPercentile(prices, 25);
        var q3 = findPercentile(prices, 75);
        var iqr = q3 - q1;

        var lowerBound = q1 - 1.5 * iqr;
        var upperBound = q3 + 1.5 * iqr;

        var grouped = cars.stream()
                .collect(Collectors.groupingBy(
                        car -> (car.getPrice() < lowerBound || car.getPrice() > upperBound) ? "outliers" : "data",
                        Collectors.counting()
                ));

        return grouped.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue()
                ));
    }

    private static double findPercentile(List<Double> sortedList, double percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * sortedList.size()) - 1;
        return sortedList.get(index);
    }
}
