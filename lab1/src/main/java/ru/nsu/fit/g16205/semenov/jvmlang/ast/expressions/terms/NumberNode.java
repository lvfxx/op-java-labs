package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

public class NumberNode implements ExpressionNode {
    private final int number;

    public NumberNode(int number) {
        this.number = number;
    }

    @Override
    public Type getType(Context context) {
        return Type.INTEGER;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        mv.visitLdcInsn(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

}
