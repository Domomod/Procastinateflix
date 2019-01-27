package Aktorzy;

@FunctionalInterface
public interface OnChangeListener<T> {
    /**
     * Infrumje sluchaczy o zmianie obiektu, poprzez przekazanie obiektu o nowym stanie.
     *
     * @param object nowy stan obiektu
     */
    void onChange(T object);
}
