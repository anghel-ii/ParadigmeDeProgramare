package model.values;

import model.types.BoolType;
import model.types.Type;

public final class BoolValue implements Value {

    private final boolean val;

    public BoolValue(boolean v) {
        this.val = v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }

}
