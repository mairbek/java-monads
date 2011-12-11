package com.github.mairbek.monad;

import com.google.common.base.Function;

public interface Bindable<T> {

    <E> Bindable<E> bind(Function<T, Bindable<E>> function);

}
