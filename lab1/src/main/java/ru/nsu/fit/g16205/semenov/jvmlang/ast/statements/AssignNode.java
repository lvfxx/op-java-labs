package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms.IdentifierNode;

import static org.objectweb.asm.Opcodes.ISTORE;

public class AssignNode extends SequencedStatementNode {
    private final IdentifierNode identifier;
    private final ExpressionNode expression;

    public AssignNode(IdentifierNode identifier, ExpressionNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return identifier.toString() + " = " + expression.toString();
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        expression.write(mv, context);
        String varName = identifier.getIdentifier();

        if (context.isVarExists(varName)) {
            if (!context.getVarType(varName).equals(expression.getType(context)))
                throw new IllegalStateException("Invalid type specified");
            int index = context.getVarIndex(varName);
            // TODO now int only
            mv.visitVarInsn(ISTORE, index);

        } else {
            // TODO now int only
            int index = context.addVar(varName, Type.INTEGER);
            mv.visitVarInsn(ISTORE, index);
        }
    }
}
