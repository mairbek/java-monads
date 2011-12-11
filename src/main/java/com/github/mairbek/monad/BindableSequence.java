package com.github.mairbek.monad;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class BindableSequence<T> implements Bindable<T> {
    private final Iterable<Bindable<T>> sequence;

    public static <T> BindableSequence<T> create(Iterable<T> sequence) {
        ImmutableList.Builder<Bindable<T>> builder = ImmutableList.builder();
        for (T item : sequence) {
            builder.add(Identity.create(item));
        }

        return new BindableSequence<T>(builder.build());
    }

    public static <T> BindableSequence<T> create(T... sequence) {
        return BindableSequence.create(Lists.newArrayList(sequence));
    }


    private BindableSequence(Iterable<Bindable<T>> sequence) {
        this.sequence = sequence;
    }


    @Override
    public <E> Bindable<E> bind(Function<T, Bindable<E>> function) {
        List<Bindable<E>> result = Lists.newArrayList();

        for (Bindable<T> t : sequence) {
            Bindable<E> bind = t.bind(function);
            result.add(bind);
        }

        return new BindableSequence<E>(result);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("sequence", sequence)
                .toString();
    }
}
