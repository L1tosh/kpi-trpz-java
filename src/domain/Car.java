package domain;

import java.time.LocalDate;

/**
 * Represents a car with brand, model, production date, class, and price.
 */
public class Car {
    private String brand;
    private String model;
    private LocalDate productionDate;
    private String carClass;
    private double price;

    /**
     * Default constructor.
     */
    public Car() {
    }

    /**
     * Constructs a car with the specified details.
     *
     * @param brand The brand of the car.
     * @param model The model of the car.
     * @param productionDate The production date of the car.
     * @param carClass The class of the car.
     * @param price The price of the car.
     */
    public Car(String brand, String model, LocalDate productionDate, String carClass, double price) {
        this.brand = brand;
        this.model = model;
        this.productionDate = productionDate;
        this.carClass = carClass;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;

        if (Double.compare(getPrice(), car.getPrice()) != 0) return false;
        if (getBrand() != null ? !getBrand().equals(car.getBrand()) : car.getBrand() != null) return false;
        if (getModel() != null ? !getModel().equals(car.getModel()) : car.getModel() != null) return false;
        if (getProductionDate() != null ? !getProductionDate().equals(car.getProductionDate()) : car.getProductionDate() != null)
            return false;
        return getCarClass() != null ? getCarClass().equals(car.getCarClass()) : car.getCarClass() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getBrand() != null ? getBrand().hashCode() : 0;
        result = 31 * result + (getModel() != null ? getModel().hashCode() : 0);
        result = 31 * result + (getProductionDate() != null ? getProductionDate().hashCode() : 0);
        result = 31 * result + (getCarClass() != null ? getCarClass().hashCode() : 0);
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", productionDate=" + productionDate +
                ", carClass='" + carClass + '\'' +
                ", price=" + price +
                '}';
    }
}
