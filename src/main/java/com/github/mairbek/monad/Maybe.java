package com.github.mairbek.monad;

import com.google.common.base.Function;
import com.google.common.base.Objects;

public class Maybe<T> implements Bindable<T> {
    private final State<T> state;
    
    public static <T> Maybe<T> just(T value) {
        return new Maybe<T>(new Just<T>(value));
    }

    public static <T> Maybe<T> nothing() {
        return new Maybe<T>((State<T>) Nothing.INSTANCE);
    }

    private Maybe(State<T> state) {
        this.state = state;
    }


    @Override
    public <E> Bindable<E> bind(final Function<T, Bindable<E>> function) {
        return state.accept(new StateVisitor<T, Bindable<E>>() {
            @Override
            public Bindable<E> visitJust(T value) {
                return function.apply(value);
            }

            @Override
            public Bindable<E> visitNothing() {
                return nothing();
            }
        });
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("state", state)
                .toString();

    }

    private static interface State<T> {
        
        <E> E accept(StateVisitor<T, E> visitor);
        
    }
    
    private static interface StateVisitor<T, E> {
        E visitJust(T value);
        
        E visitNothing();
    }
    
    private static class Just<T> implements State<T> {
        private final T value;

        private Just(T value) {
            this.value = value;
        }

        @Override
        public <E> E accept(StateVisitor<T, E> visitor) {
            return visitor.visitJust(value);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("value", value)
                    .toString();
        }
    }

    private static enum Nothing implements State<Object> {
        INSTANCE;

        @Override
        public <E> E accept(StateVisitor<Object, E> visitor) {
            return visitor.visitNothing();
        }


        @Override
        public String toString() {
            return "Nothing";
        }
    }
}
