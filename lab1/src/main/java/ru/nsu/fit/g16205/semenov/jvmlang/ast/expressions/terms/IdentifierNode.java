package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ILOAD;

public class IdentifierNode implements ExpressionNode {
    private final String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public Type getType(Context context) {
        return context.getVarType(identifier);
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        int index = context.getVarIndex(identifier);
        if (index == -1)
            throw new IllegalStateException("Undefined variable: " + identifier);
        Type type = context.getVarType(identifier);
        switch (type) {
            case INTEGER:
            case BOOLEAN:
                mv.visitVarInsn(ILOAD, index);
                break;
            case STRING:
                mv.visitVarInsn(ALOAD, index);
                break;
            default:
                throw new AssertionError("Unknown type specified: " + type);
        }
    }
}
