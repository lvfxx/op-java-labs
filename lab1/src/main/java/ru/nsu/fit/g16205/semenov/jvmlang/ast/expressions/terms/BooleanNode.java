package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;

public class BooleanNode extends TermNode {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Type getType(Context context) {
        return Type.BOOLEAN;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        if (value) {
            mv.visitLdcInsn(1);
        } else {
            mv.visitLdcInsn(0);
        }
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
