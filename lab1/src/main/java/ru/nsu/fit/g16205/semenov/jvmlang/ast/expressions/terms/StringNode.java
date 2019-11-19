package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

public class StringNode implements ExpressionNode {
    private final String value;

    public StringNode(String value) {
        this.value = value
                .replace("\\b", "\b")
                .replace("\\t", "\t")
                .replace("\\n", "\n")
                .replace("\\f", "\f")
                .replace("\\r", "\r")
                .replace("\\\\", "\\")
                .replace("\\\"", "\"");
    }

    @Override
    public Type getType(Context context) {
        return Type.STRING;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        mv.visitLdcInsn(value);
    }

    @Override
    public String toString() {
        return '"' + value + '"';
    }
}
