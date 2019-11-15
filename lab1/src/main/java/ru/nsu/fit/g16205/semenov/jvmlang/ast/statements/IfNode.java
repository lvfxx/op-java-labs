package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.IF_ICMPEQ;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;

public class IfNode extends SequencedStatementNode {
    private final ExpressionNode condition;
    private final StatementNode body;

    public IfNode(ExpressionNode condition, StatementNode body) {
        this.condition = condition;
        this.body = body;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public StatementNode getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "if (" + condition.toString() + ")\n" + body.toString() + "\nfi";
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        Label label = new Label();
        // todo add type check
        condition.write(mv, context);
        mv.visitLdcInsn(1);
        mv.visitJumpInsn(IF_ICMPNE, label);
        body.write(mv, context);
        mv.visitLabel(label);
    }
}
