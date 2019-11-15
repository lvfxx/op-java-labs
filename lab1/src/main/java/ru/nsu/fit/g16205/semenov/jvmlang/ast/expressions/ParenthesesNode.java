package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;

public class ParenthesesNode implements ExpressionNode {
    private final ExpressionNode expression;

    public ParenthesesNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + expression.toString() + ")";
    }

    @Override
    public Type getType(Context context) {
        return expression.getType(context);
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        expression.write(mv, context);
    }
}
