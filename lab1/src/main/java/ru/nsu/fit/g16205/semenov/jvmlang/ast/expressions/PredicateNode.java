package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;

import static org.objectweb.asm.Opcodes.GOTO;

public interface PredicateNode extends ExpressionNode {

    /**
     * Writes boolean expression as condition bytecode, returns label, where to jump if false.
     * If true, next instruction after condition will be executed
     */
    Label writeAsJump(MethodVisitor mv, Context context);

    @Override
    default void write(MethodVisitor mv, Context context) {
        Label ifFalseLabel = writeAsJump(mv, context);
        Label end = new Label();

        mv.visitLdcInsn(1);
        mv.visitJumpInsn(GOTO, end);
        mv.visitLabel(ifFalseLabel);
        mv.visitLdcInsn(0);
        mv.visitLabel(end);
    }

    @Override
    default Type getType(Context context) {
        return Type.BOOLEAN;
    }
}
