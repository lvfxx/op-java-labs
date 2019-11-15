package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;

import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

public class PrintNode extends SequencedStatementNode {
    private final ExpressionNode expression;

    public PrintNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        String printMethodDescriptor;
        switch (expression.getType(context)) {
            case INTEGER:
                printMethodDescriptor = "(I)V";
                break;
            case STRING:
                printMethodDescriptor = "(Ljava/lang/String;)V";
                break;
            case BOOLEAN:
                printMethodDescriptor = "(Z)V";
                break;
            default:
                throw new AssertionError("Unknown type specified");
        }
        expression.write(mv, context);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", printMethodDescriptor, false);
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
