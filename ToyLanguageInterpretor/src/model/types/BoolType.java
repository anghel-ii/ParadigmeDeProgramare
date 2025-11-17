package model.types;

import model.values.BoolValue;
import model.values.Value;

import java.util.Objects;

public final class BoolType implements Type {

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public int hashCode() {
        return Objects.hash("bool");
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
