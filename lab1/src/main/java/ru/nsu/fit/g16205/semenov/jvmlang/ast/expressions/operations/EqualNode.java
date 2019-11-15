package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IF_ICMPEQ;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.INTEGER;

public class EqualNode extends BinaryOperationNode {

    public EqualNode(ExpressionNode left, ExpressionNode right) {
        super("==", left, right);
    }

    @Override
    public Type getType(Context context) {
        return Type.BOOLEAN;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        // TODO add another types
        // TODO как-то прокидвать лейблы???
        if (INTEGER.equals(getLeft().getType(context)) && INTEGER.equals(getRight().getType(context))) {
            Label pushTrue = new Label();
            Label end = new Label();
            getLeft().write(mv ,context);
            getRight().write(mv ,context);
            mv.visitJumpInsn(IF_ICMPEQ, pushTrue);
            mv.visitLdcInsn(0);
            mv.visitJumpInsn(GOTO, end);
            mv.visitLabel(pushTrue);
            mv.visitLdcInsn(1);
            mv.visitLabel(end);
        }
    }
}
