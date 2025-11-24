package model.values;

import model.types.IntType;
import model.types.Type;

public final class IntValue implements Value{

    private final int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

}
