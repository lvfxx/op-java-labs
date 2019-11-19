package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations;

import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static ru.nsu.fit.g16205.semenov.jvmlang.Type.INTEGER;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.STRING;

public abstract class BinaryOperationNode implements ExpressionNode {

    private final String operatorSign;
    private final ExpressionNode left;
    private final ExpressionNode right;

    public BinaryOperationNode(String operatorSign, ExpressionNode left, ExpressionNode right) {
        this.operatorSign = operatorSign;
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
    public Type getType(Context context) {
        Type leftType = getLeft().getType(context);
        Type rightType = getRight().getType(context);
        if (INTEGER.equals(leftType) && INTEGER.equals(rightType)) {
            return INTEGER;
        } else {
            throw new IllegalStateException("Invalid arguments types: " + leftType + " and " + rightType);
        }
    }

    @Override
    public String toString() {
        return left.toString() + " " + operatorSign + " " + right.toString();
    }
}
