package ru.nsu.fit.g16205.semenov.jvmlang.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.RETURN;

public class ClassCreator {
    private final ClassWriter cw = new ClassWriter(0);
    private final MethodVisitor mv;
    private final Context context = new Context();
    private boolean visitEnded = false;

    public ClassCreator(String fcn) {
        cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER, fcn, null, "java/lang/Object", null);
        initDefaultConstructor();
        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        context.enterScope();
    }

    // TODO handle exceptions
    public void writeToMain(BytecodeProvider bp) {
        if (visitEnded) throw new IllegalStateException("Visitor ended its job.");
        bp.write(mv, context);
    }

    public byte[] getBytecode() {
        if (!visitEnded) {
            mv.visitInsn(RETURN);
            mv.visitMaxs(10, 10); // TODO how to determine correct values???
            mv.visitEnd();
            cw.visitEnd();
            visitEnded = true;
        }
        return cw.toByteArray();
    }

    private void initDefaultConstructor() {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
}
