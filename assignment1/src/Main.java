import model.*;
import controller.*;
import repo.Repository;
import view.View;

public class Main {
    public static void main(String[] args) throws Exception {

        View ParkingView =new View(new Controller(new Repository()));

        ParkingView.addVehicle(new Motorcycle("Kawasaki","Ninja","red"));
        ParkingView.addVehicle(new Bicycle("YT","Tues","red"));
        ParkingView.addVehicle(new Bicycle("YT","Tues","blue"));
        ParkingView.addVehicle(new Car("Nissan","Skyline","blue"));
        ParkingView.addVehicle(new Car("Opel","Corsa","red"));

        ParkingView.removeVehicles(2);
        ParkingView.removeVehicles(12);

        ParkingView.printVehiclesByColor("red");
        ParkingView.printVehiclesByColor("blue");
    }
}
