package view;

import controller.Controller;
import model.IVehicle;

import static java.lang.IO.print;

public class View {
    private final Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void printVehiclesByColor(String color){
        for (IVehicle vehicle : controller.getVehiclesByColor(color)) {
            if(vehicle==null) break;
            System.out.println(vehicle);
        }
    }

    public void removeVehicles(int index)  {
        try {
            controller.removeVehicle(index);
        }
        catch (Exception e) {
            print(e.getMessage() + '\n');
        }


    }

    public void addVehicle(IVehicle vehicle)  {
        try {
            controller.addVehicle(vehicle);
        }
        catch (Exception e) {
            print(e.getMessage() + '\n');
        }
    }
}
