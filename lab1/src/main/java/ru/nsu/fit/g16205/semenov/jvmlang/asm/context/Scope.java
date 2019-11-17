package ru.nsu.fit.g16205.semenov.jvmlang.asm.context;

import ru.nsu.fit.g16205.semenov.jvmlang.Type;

import java.util.HashMap;
import java.util.Map;

public class Scope {

    private final Map<String, VarData> varDataMap = new HashMap<>();
    private final LocalVarArray varArray;

    public Scope(LocalVarArray varArray) {
        this.varArray = varArray;
    }

    public VarData declareVar(String name, Type type) {
        if (isVarDeclared(name))
            throw new IllegalStateException("Variable " + name + " already declared");
        VarData varData = new VarData(type, varArray.takeFree());
        varDataMap.put(name, varData);
        return varData;
    }

    public boolean isVarDeclared(String name) {
        return varDataMap.containsKey(name);
    }

    VarData getVarData(String name) {
        if (!isVarDeclared(name))
            throw new IllegalStateException("Variable " + name + " not declared");
        return varDataMap.get(name);
    }

    void destruct() {
        varDataMap.values().forEach(varData -> varArray.free(varData.index));
    }

}
