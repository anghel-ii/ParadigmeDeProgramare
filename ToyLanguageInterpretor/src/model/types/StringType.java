package model.types;

import model.values.StringValue;
import model.values.Value;

import java.util.Objects;

public final class StringType implements Type {

    @Override
    public boolean equals(Object another){return another instanceof StringType;}

    @Override
    public int hashCode(){return Objects.hash("string");}

    @Override
    public String toString(){return "string";}

    @Override
    public Value defaultValue() {return new StringValue("");}
}
