package ru.nsu.fit.g16205.semenov.jvmlang;

public enum Type {
    INTEGER("Int"),
    STRING("String"),
    BOOLEAN("Bool"),
    TYPE("Type");

    private final String string;

    Type(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    public static Type fromString(String s) {
        for (Type type : Type.values()) {
            if (type.string.equals(s))
                return type;
        }
        throw new IllegalArgumentException("Invalid string specified: " + s);
    }
}
