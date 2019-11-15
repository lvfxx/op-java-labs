package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;

import static org.objectweb.asm.Opcodes.ILOAD;

public class IdentifierNode extends TermNode {
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
        if (index == -1) throw new IllegalStateException("Variable " + identifier + " undefined");
        // TODO now int only
        mv.visitVarInsn(ILOAD, index);
    }
}
