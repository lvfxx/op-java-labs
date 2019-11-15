package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations.predicates;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.IF_ICMPLE;

public class LessOrEqualNode extends AbstractCompareNode {

    public LessOrEqualNode(ExpressionNode left, ExpressionNode right) {
        super("<=", left, right);
    }

    @Override
    public void writeIntCompare(MethodVisitor mv, Label ifFalse) {
        mv.visitJumpInsn(IF_ICMPLE, ifFalse);
    }

}
