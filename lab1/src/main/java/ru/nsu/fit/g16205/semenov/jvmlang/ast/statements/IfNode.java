package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.PredicateNode;

import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.BOOLEAN;

public class IfNode extends SequencedStatementNode {
    private final ExpressionNode condition;
    private final StatementNode body;

    public IfNode(ExpressionNode condition, StatementNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        final Label endIf;
        if (condition instanceof PredicateNode) {
            endIf = ((PredicateNode) condition).writeAsJump(mv, context);
        } else {
            endIf = new Label();
            Type conditionType = condition.getType(context);
            if (!BOOLEAN.equals(conditionType))
                throw new IllegalStateException("Invalid type specified: " + conditionType);
            condition.write(mv, context);
            mv.visitLdcInsn(1);
            mv.visitJumpInsn(IF_ICMPNE, endIf);
        }
        context.enterScope();
        body.write(mv, context);
        context.leaveScope();
        mv.visitLabel(endIf);
    }

    @Override
    public String toString() {
        return "if (" + condition.toString() + ")\n" + body.toString() + "\nfi";
    }
}
