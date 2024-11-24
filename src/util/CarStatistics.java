package util;

/**
 * Holds statistics about car prices, such as min, max, average, and standard deviation.
 */
public class CarStatistics {

    private final double minPrice;
    private final double maxPrice;
    private final double averagePrice;
    private final double standardDeviation;

    public CarStatistics(double minPrice, double maxPrice, double averagePrice, double standardDeviation) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.averagePrice = averagePrice;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public String toString() {
        return "CarStatistics{" +
                "minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", averagePrice=" + averagePrice +
                ", standardDeviation=" + standardDeviation +
                '}';
    }
}
