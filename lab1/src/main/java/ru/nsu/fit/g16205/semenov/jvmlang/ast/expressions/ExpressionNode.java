package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions;

import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.AstNode;

public abstract class ExpressionNode extends AstNode {

    public abstract Type getType(Context context);

}
