package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations.predicates;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.PredicateNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations.BinaryOperationNode;

public abstract class AbstractCompareNode extends BinaryOperationNode implements PredicateNode {

    public AbstractCompareNode(String operatorSign, ExpressionNode left, ExpressionNode right) {
        super(operatorSign, left, right);
    }

    public abstract void writeIntCompare(MethodVisitor mv, Label ifFalse);

    @Override
    public Label writeAsJump(MethodVisitor mv, Context context) {
        Label ifFalse = new Label();
        Type leftType = getLeft().getType(context);
        Type rightType = getRight().getType(context);

        if (leftType.equals(rightType)) {
            getRight().write(mv, context);
            getLeft().write(mv, context);
            switch (leftType) {
                case INTEGER:
                    writeIntCompare(mv, ifFalse);
                    break;
                case STRING:
                case BOOLEAN:
                    throw new IllegalStateException("Unsupported type specified: " + leftType);
                default:
                    throw new AssertionError("Unknown type specified: " + leftType);
            }
        } else {
            throw new IllegalStateException("Type mismatch: " + leftType + " and " + rightType);
        }
        return ifFalse;
    }
}
