package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms.IdentifierNode;

import static org.objectweb.asm.Opcodes.ASTORE;
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
        String varName = identifier.getIdentifier();
        Type expressionType = expression.getType(context);
        int index;

        if (context.isVarExists(varName)) {
            if (!context.getVarType(varName).equals(expressionType))
                throw new IllegalStateException("Type mismatch");
            index = context.getVarIndex(varName);
        } else {
            index = context.addVar(varName, expressionType);
        }

        expression.write(mv, context);
        switch (expressionType) {
            case INTEGER:
            case BOOLEAN:
                mv.visitVarInsn(ISTORE, index);
                break;
            case STRING:
                mv.visitVarInsn(ASTORE, index);
                break;
            default:
                throw new AssertionError("Unknown type specified");
        }
    }
}
