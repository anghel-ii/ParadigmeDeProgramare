package repo;

import model.IVehicle;

public interface IRepository {
    public void addVehicle(IVehicle vehicle) throws Exception;
    public IVehicle[] getVehicles();
    public void RemoveVehicle(int index) throws Exception;
    public int getCount();
}
