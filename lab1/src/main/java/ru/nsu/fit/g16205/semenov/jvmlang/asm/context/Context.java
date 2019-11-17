package ru.nsu.fit.g16205.semenov.jvmlang.asm.context;

import ru.nsu.fit.g16205.semenov.jvmlang.Type;

import java.util.Deque;
import java.util.LinkedList;

public class Context {

    private final LocalVarArray varArray = new LocalVarArray();
    private final Deque<Scope> scopes = new LinkedList<>();

    public void enterScope() {
        scopes.addFirst(new Scope(varArray));
    }

    public void leaveScope() {
        Scope scope = scopes.pollFirst();
        if (scope == null) throw new IllegalStateException("Scopes stack is empty");
        scope.destruct();
    }

    public Scope getCurrentScope() {
        Scope scope = scopes.peekFirst();
        if (scope == null) throw new IllegalStateException("Scopes stack is empty");
        return scope;
    }

    public Type getVarType(String name) {
        return getVarData(name).type;
    }

    public int getVarIndex(String name) {
        return getVarData(name).index;
    }

    public VarData getVarData(String name) {
        return scopes.stream()
                .filter(scope -> scope.isVarDeclared(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Undefined variable: " + name))
                .getVarData(name);
    }

}
