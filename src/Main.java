import service.CarService;
import util.CarOutliers;
import util.CarStatisticsCollector;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var carService = new CarService();

        var gatheredCars = carService.gatherCars("Kia", 10);
        var filteredCars = carService.filterByTime(gatheredCars,
                LocalDate.now().minusMonths(60),
                LocalDate.now().minusMonths(12));

        var groupedCars = carService.groupByClass(filteredCars);

        groupedCars.values().forEach(System.out::println);

        var carStatistics = filteredCars.stream()
                .collect(CarStatisticsCollector.toCarStatistics());
        System.out.println(carStatistics);

        var statistic = CarOutliers.analyzeOutliers(filteredCars);
        System.out.println(statistic);

    }
}