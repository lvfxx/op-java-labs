package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.PredicateNode;

import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static ru.nsu.fit.g16205.semenov.jvmlang.Type.BOOLEAN;

public class LoopNode extends SequencedStatementNode {
    private final ExpressionNode condition;
    private final StatementNode body;

    public LoopNode(ExpressionNode condition, StatementNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "loop (" + condition.toString() + ")\n" + body.toString() + "\npool";
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        final Label beginLoop = new Label();
        final Label endLoop;
        mv.visitLabel(beginLoop);
        if (condition instanceof PredicateNode) {
            endLoop = ((PredicateNode) condition).writeAsJump(mv, context);
        } else {
            endLoop = new Label();
            Type conditionType = condition.getType(context);
            if (!BOOLEAN.equals(conditionType))
                throw new IllegalStateException("Invalid type specified: " + conditionType);
            condition.write(mv, context);
            mv.visitLdcInsn(1);
            mv.visitJumpInsn(IF_ICMPNE, endLoop);
        }
        context.enterScope();
        body.write(mv, context);
        context.leaveScope();
        mv.visitJumpInsn(GOTO, beginLoop);
        mv.visitLabel(endLoop);
    }
}
