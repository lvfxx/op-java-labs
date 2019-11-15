package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations;

import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

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
    public String toString() {
        return left.toString() + " " + operatorSign + " " + right.toString();
    }
}
