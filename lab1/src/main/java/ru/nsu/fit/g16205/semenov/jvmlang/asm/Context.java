package ru.nsu.fit.g16205.semenov.jvmlang.asm;

import ru.nsu.fit.g16205.semenov.jvmlang.Type;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Map<String, VarData> varDataMap = new HashMap<>();
    private int varArraySize;

    /**
     *
     * @param name value name
     * @param type value type
     * @return -1 if variable exists, index in local variable array otherwise
     */
    public int addVar(String name, Type type) {
        if (varDataMap.containsKey(name))
            throw new IllegalStateException("Variable with given name already exists");
        varDataMap.put(name, new VarData(type, ++varArraySize));
        return varArraySize;
    }

    public boolean isVarExists(String name) {
        return varDataMap.containsKey(name);
    }

    public Type getVarType(String name) {
        if (varDataMap.containsKey(name))
            return varDataMap.get(name).type;
        throw new IllegalArgumentException("Undefined variable");
    }

    public int getVarIndex(String name) {
        if (varDataMap.containsKey(name))
            return varDataMap.get(name).index;
        throw new IllegalArgumentException("Undefined variable");
    }

    private static class VarData {
        final Type type;
        final int index;

        VarData(Type type, int index) {
            this.type = type;
            this.index = index;
        }
    }

}
