package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;

import static org.objectweb.asm.Opcodes.GOTO;

public class BreakNode extends SequencedStatementNode {

    @Override
    public void write(MethodVisitor mv, Context context) {
        Label endOfBlock = context.getEndOfBlock();
        if (endOfBlock == null) {
            throw new IllegalStateException("End of block label undefined");
        }
        mv.visitJumpInsn(GOTO, endOfBlock);
    }

    @Override
    public String toString() {
        return "break";
    }
}
