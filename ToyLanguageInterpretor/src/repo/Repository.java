package repo;

import state.PrgState;

public class Repository implements IRepository {
    private final PrgState state;

    public Repository(PrgState state) {
        this.state = state;
    }

    @Override
    public PrgState getCrtPrg() {
        return state;
    }
}
