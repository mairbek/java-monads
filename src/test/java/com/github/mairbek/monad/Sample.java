package com.github.mairbek.monad;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;

public class Sample {

    public static void main(String[] args) {

        Bindable<Integer> result = Identity.create(5)
                .bind(new Function<Integer, Bindable<Integer>>() {
                    @Override
                    public Maybe<Integer> apply(@Nullable Integer integer) {
                        if (integer == null) {
                            return Maybe.nothing();
                        }
                        return Maybe.just(integer * 10);
                    }
                })
                .bind(new Function<Integer, Bindable<Integer>>() {
                    @Override
                    public Bindable<Integer> apply(@Nullable Integer integer) {
                        return BindableSequence.create(integer, integer + 10);
                    }
                })
                .bind(new Function<Integer, Bindable<Integer>>() {
                    @Override
                    public Maybe<Integer> apply(@Nullable Integer integer) {
                        if (integer == null) {
                            return Maybe.nothing();
                        }
                        return Maybe.just(integer + 10);
                    }
                });

        System.out.println(result);
    }
}
