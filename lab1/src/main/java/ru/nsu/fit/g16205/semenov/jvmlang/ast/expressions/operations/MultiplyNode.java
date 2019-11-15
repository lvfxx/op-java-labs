package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.IMUL;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.INTEGER;

public class MultiplyNode extends BinaryOperationNode {
    public MultiplyNode(ExpressionNode left, ExpressionNode right) {
        super("*", left, right);
    }

    @Override
    public Type getType(Context context) {
        return INTEGER;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        Type leftType = getLeft().getType(context);
        Type rightType = getRight().getType(context);
        if (!(INTEGER.equals(leftType) && INTEGER.equals(rightType)))
            throw new IllegalStateException("Invalid arguments types: " + leftType + " and " + rightType);
        getLeft().write(mv, context);
        getRight().write(mv, context);
        mv.visitInsn(IMUL);
    }
}
