package com.github.mairbek.monad;

import com.google.common.base.Function;
import com.google.common.base.Objects;

public class Identity<T> implements Bindable<T> {
    private final T value;
    
    public static <T> Identity<T> create(T value) {
        return new Identity<T>(value);
    }

    private Identity(T value) {
        this.value = value;
    }

    @Override
    public <E> Bindable<E> bind(Function<T, Bindable<E>> function) {
        return function.apply(value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .toString();
    }
}
