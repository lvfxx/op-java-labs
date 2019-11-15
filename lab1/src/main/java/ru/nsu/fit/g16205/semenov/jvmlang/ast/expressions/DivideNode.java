package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;

import static org.objectweb.asm.Opcodes.IDIV;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.INTEGER;

public class DivideNode extends ExpressionNode {
    private final ExpressionNode left;
    private final ExpressionNode right;

    public DivideNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left.toString() + " / " + right.toString();
    }

    @Override
    public Type getType(Context context) {
        if (INTEGER.equals(right.getType(context)) && INTEGER.equals(left.getType(context)))
            return INTEGER;
        throw new IllegalStateException("Invalid arguments types");
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        // TODO order of application??
        left.write(mv, context);
        right.write(mv, context);
        mv.visitInsn(IDIV);
    }
}
