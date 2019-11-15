package ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions;

import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.Context;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.AstNode;

public interface ExpressionNode extends AstNode {

    Type getType(Context context);

}
