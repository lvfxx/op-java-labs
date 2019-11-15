package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.PredicateNode;

import static org.objectweb.asm.Opcodes.GOTO;

public class BooleanNode implements PredicateNode {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Type getType(Context context) {
        return Type.BOOLEAN;
    }

    @Override
    public Label writeAsJump(MethodVisitor mv, Context context) {
        Label ifFalse = new Label();
        if (!value)
            mv.visitJumpInsn(GOTO, ifFalse);
        return ifFalse;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        if (value)
            mv.visitLdcInsn(1);
        else
            mv.visitLdcInsn(0);
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
