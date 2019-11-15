package ru.nsu.fit.g16205.semenov.jvmlang.asm;

import org.objectweb.asm.MethodVisitor;

@FunctionalInterface
public interface BytecodeProvider {

    void write(MethodVisitor mv, Context context);

}
