package repo;

import model.IVehicle;

public class Repository implements IRepository {
    private int count;
    private IVehicle[] vehicles;
    public static int SIZE = 2;

    public Repository() {
        this.count = 0;
        this.vehicles = new IVehicle[SIZE];
    }

    @Override
    public void addVehicle(IVehicle vehicle) throws Exception {
        if (count < SIZE) {
            vehicles[count] = vehicle;
            count++;
        } else {
            throw new RepoException("full parking");
        }


    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public IVehicle[] getVehicles() {
        return vehicles;
    }

    @Override
    public void RemoveVehicle(int index) throws RepoException {
        if(index > 0 && index < count) {
            for(int i = index; i < count - 1; i++) {
                vehicles[i] = vehicles[i + 1];
            }
            vehicles[count - 1] = null;
            count--;
        }
        else throw new RepoException("invalid index");
    }
}
