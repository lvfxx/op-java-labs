package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.INTEGER;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.STRING;

public class AddNode extends BinaryOperationNode {

    public AddNode(ExpressionNode left, ExpressionNode right) {
        super("+", left, right);
    }

    @Override
    public Type getType(Context context) {
        Type leftType = getLeft().getType(context);
        Type rightType = getRight().getType(context);
        if (INTEGER.equals(leftType) && INTEGER.equals(rightType)) {
            return INTEGER;
        } else if (STRING.equals(leftType) && STRING.equals(rightType)) {
            return STRING;
        } else {
            throw new IllegalStateException("Invalid arguments types: " + leftType + " and " + rightType);
        }
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        Type leftType = getLeft().getType(context);
        Type rightType = getRight().getType(context);
        if (INTEGER.equals(leftType) && INTEGER.equals(rightType)) {
            getLeft().write(mv, context);
            getRight().write(mv, context);
            mv.visitInsn(IADD);
        } else if (STRING.equals(leftType) && STRING.equals(rightType)) {
            getLeft().write(mv, context);
            getRight().write(mv, context);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "concat",
                    "(Ljava/lang/String;)Ljava/lang/String;", false);
        } else {
            throw new IllegalStateException("Invalid arguments types: " + leftType + " and " + rightType);
        }
    }
}
