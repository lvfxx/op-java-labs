package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations.predicates;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.PredicateNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations.BinaryOperationNode;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.*;

public class EqualNode extends BinaryOperationNode implements PredicateNode {

    public EqualNode(ExpressionNode left, ExpressionNode right) {
        super("==", left, right);
    }

    @Override
    public Label writeAsJump(MethodVisitor mv, Context context) {
        Label ifFalse = new Label();
        Type leftType = getLeft().getType(context);
        Type rightType = getRight().getType(context);

        if (leftType.equals(rightType)) {
            getLeft().write(mv, context);
            getRight().write(mv, context);
            switch (leftType) {
                case INTEGER:
                case BOOLEAN:
                    mv.visitJumpInsn(IF_ICMPNE, ifFalse);
                    break;
                case STRING:
                    mv.visitJumpInsn(IF_ACMPNE, ifFalse);
                    break;
                default:
                    throw new AssertionError("Unknown type specified");
            }
        } else {
            mv.visitJumpInsn(GOTO, ifFalse);
        }
        return ifFalse;
    }

}
