package util;

import domain.Car;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Generates random car data for testing purposes.
 */
public class CarGenerator {
    private final List<String> brands;
    private final List<String> classes;
    private final Random random;

    /**
     * Initializes the car generator with predefined lists of car brands and classes.
     */
    public CarGenerator() {
        brands = Arrays.asList(
                "Toyota", "BMW", "Mercedes", "Ford", "Audi",
                "Kia", "Nissan", "Daewoo", "Dacia", "Volkswagen",
                "Chevrolet", "Honda", "Hyundai", "Mazda", "Subaru",
                "Mitsubishi", "Volvo", "Tesla", "Porsche", "Jaguar"
        );
        classes = Arrays.asList("A", "B", "C", "D", "E", "S");
        random = new Random();
    }

    /**
     * Generates a stream of random cars.
     *
     * @return A stream of randomly generated cars.
     */
    public Stream<Car> generateCars() {
        return Stream.generate(() -> {
            var brand = brands.get(random.nextInt(brands.size()));
            var model = "Model" + random.nextInt(100);
            var productionDate = LocalDate.now().minusMonths(random.nextInt(240));
            var carClass = classes.get(random.nextInt(classes.size()));
            var price = (20_000 + random.nextInt(200_000)) * 41.36 ;
            return new Car(brand, model, productionDate, carClass, price);
        });
    }
}
