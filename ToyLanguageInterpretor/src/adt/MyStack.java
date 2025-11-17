package adt;

import exceptions.MyException;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {

    private final Deque<T> stack = new ArrayDeque<>();

    @Override
    public T pop() {
        if (stack.isEmpty()) throw new MyException("Empty stack");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> toList() {
        return new ArrayList<>(stack);
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
