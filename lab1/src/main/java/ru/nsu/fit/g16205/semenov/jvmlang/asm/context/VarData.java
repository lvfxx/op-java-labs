package ru.nsu.fit.g16205.semenov.jvmlang.asm.context;

import ru.nsu.fit.g16205.semenov.jvmlang.Type;

public class VarData {
    final Type type;
    final int index;

    VarData(Type type, int index) {
        this.type = type;
        this.index = index;
    }

    public Type getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }
}
