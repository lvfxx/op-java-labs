package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;

public class SequenceNode extends StatementNode {
    private final StatementNode left;
    private final StatementNode right;

    public SequenceNode(StatementNode left, StatementNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        left.write(mv, context);
        right.write(mv, context);
    }

    @Override
    public String toString() {
        return left.toString() + '\n' + right.toString();
    }
}
