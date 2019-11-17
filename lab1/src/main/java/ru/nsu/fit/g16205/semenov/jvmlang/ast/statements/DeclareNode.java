package ru.nsu.fit.g16205.semenov.jvmlang.ast.statements;

import org.objectweb.asm.MethodVisitor;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.Scope;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.context.VarData;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms.IdentifierNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms.TypeNode;

import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.ISTORE;

public class DeclareNode extends SequencedStatementNode {

    private final IdentifierNode identifier;
    private final TypeNode varType;

    public DeclareNode(IdentifierNode identifier, TypeNode varType) {
        this.identifier = identifier;
        this.varType = varType;
    }

    @Override
    public void write(MethodVisitor mv, Context context) {
        String varName = identifier.getIdentifier();
        Scope scope = context.getCurrentScope();
        if (scope.isVarDeclared(varName))
            throw new IllegalStateException("Variable " + varName + " is already declared in this block");
        VarData varData = scope.declareVar(varName, varType.getType());
        writeDefaultValue(mv, varData);
    }

    private static void writeDefaultValue(MethodVisitor mv, VarData varData) {
        switch (varData.getType()) {
            case INTEGER:
            case BOOLEAN:
                mv.visitLdcInsn(0);
                mv.visitVarInsn(ISTORE, varData.getIndex());
                break;
            case STRING:
                mv.visitLdcInsn("");
                mv.visitVarInsn(ASTORE, varData.getIndex());
                break;
            default:
                throw new AssertionError("Unknown type specified: " + varData.getType());
        }
    }

    @Override
    public String toString() {
        return "var " + identifier.toString() + ": " + varType.toString();
    }
}
