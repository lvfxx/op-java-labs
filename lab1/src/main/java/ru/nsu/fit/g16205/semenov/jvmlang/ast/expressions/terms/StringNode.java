package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;

public class StringNode extends TermNode {
    private final String value;

    // TODO fix espe characters that as strings
    public StringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Type getType(Context context) {
        return Type.STRING;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        mv.visitLdcInsn(value);
    }

    @Override
    public String toString() {
        return '"' + value + '"';
    }
}
