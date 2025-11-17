package model;

public class Motorcycle implements IVehicle {
    private final String make,model,color;

    public Motorcycle(String make, String model, String color) {
        this.color = color;
        this.make = make;
        this.model = model;
    }

    public String toString() {
        return make + " " + model + " in " + color;
    }

    @Override
    public String getColor() {
        return color;
    }
}
