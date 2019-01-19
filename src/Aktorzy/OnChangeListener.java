package Aktorzy;

@FunctionalInterface
public interface OnChangeListener<T> {
    void onChange(T object);
}
