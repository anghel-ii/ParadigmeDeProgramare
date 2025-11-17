package model.types;

import model.values.IntValue;
import model.values.Value;

import java.util.Objects;

public final class IntType implements Type {

    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public int hashCode() {
        return Objects.hash("int");
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
