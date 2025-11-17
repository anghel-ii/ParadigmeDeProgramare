package controller;

import model.IVehicle;
import repo.IRepository;
import repo.RepoException;
import repo.Repository;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void addVehicle(IVehicle vehicle) throws Exception {
        repo.addVehicle(vehicle);
    }

    public void removeVehicle(int index) throws Exception {
        repo.RemoveVehicle(index);
    }

    public IVehicle[] getVehiclesByColor(String color) {
        IVehicle[] vehicles = repo.getVehicles();
        IVehicle[] filteredVehicles = new IVehicle[Repository.SIZE];
        int filteredCount = 0;
        for (int i = 0; i < repo.getCount(); i++) {
            if (vehicles[i].getColor().equals(color)){
                filteredVehicles[filteredCount] = vehicles[i];
                filteredCount++;
            }
        }
        return filteredVehicles;
    }
}
