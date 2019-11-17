package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

public class TypeNode implements ExpressionNode {

    private final Type type;

    public TypeNode(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
    }

    @Override
    public Type getType(Context context) {
        return Type.TYPE;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
