package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.VarData;
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

    @Override
    public String toString() {
        return identifier.toString() + " = " + expression.toString();
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        VarData varData = context.getVarData(identifier.getIdentifier());
        Type expressionType = expression.getType(context);

        if (!varData.getType().equals(expressionType))
            throw new IllegalStateException("Type mismatch: assigning " + expressionType +
                    " expression to " + varData.getType() + " variable");

        expression.write(mv, context);
        switch (expressionType) {
            case INTEGER:
            case BOOLEAN:
                mv.visitVarInsn(ISTORE, varData.getIndex());
                break;
            case STRING:
                mv.visitVarInsn(ASTORE, varData.getIndex());
                break;
            default:
                throw new AssertionError("Unknown type specified: " + expressionType);
        }
    }
}
