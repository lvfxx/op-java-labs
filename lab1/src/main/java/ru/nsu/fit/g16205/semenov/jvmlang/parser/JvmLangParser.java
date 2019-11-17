package ru.nsu.fit.g16205.semenov.jvmlang.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import ru.nsu.fit.g16205.semenov.jvmlang.Type;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.AstNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ExpressionNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.ParenthesesNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations.*;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.operations.predicates.*;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms.*;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.statements.*;

@BuildParseTree
public class JvmLangParser extends BaseParser<AstNode> {

    @Override
    protected Rule fromStringLiteral(String string) {
        return string.endsWith(" ") ?
                Sequence(String(string.substring(0, string.length() - 1)), OptWhiteSpace()) :
                String(string);
    }

    public Rule Program() {
        return Sequence(Seq(), Optional(NewLine()), EOI);
    }

    Rule Seq() {
        return FirstOf(
                Sequence(
                        SequencedStatement(), NewLine(), Seq(),
                        push(new SequenceNode((StatementNode) pop(1), (StatementNode) pop()))
                ),
                SequencedStatement()
        );
    }

    Rule SequencedStatement() {
        return FirstOf(Declare(), Assign(), If(), Loop(), Print());
    }

    Rule Declare() {
        return Sequence(
                "var ", Identifier(), ": ", Type(),
                push(new DeclareNode((IdentifierNode) pop(1), (TypeNode) pop()))
        );
    }

    Rule Assign() {
        return Sequence(
                Identifier(), "= ", Expression(),
                push(new AssignNode((IdentifierNode) pop(1), (ExpressionNode) pop()))
        );
    }

    Rule Loop() {
        return Sequence(
                "loop ", "( ", Expression(), ") ", NewLine(), Seq(), NewLine(), "pool ",
                push(new LoopNode((ExpressionNode) pop(1), (StatementNode) pop()))
        );
    }

    Rule If() {
        return Sequence(
                "if ", "( ", Expression(), ") ", NewLine(), Seq(), NewLine(), "fi ",
                push(new IfNode((ExpressionNode) pop(1), (StatementNode) pop()))
        );
    }

    Rule Print() {
        return Sequence("print ", "( ", Expression(), ") ",
                push(new PrintNode((ExpressionNode) pop()))
        );
    }

    Rule Expression() {
        return Add();
    }

    Rule Add() {
        return FirstOf(
                Sequence(
                        Multiply(), "+ ", Add(),
                        push(new AddNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Multiply()
        );
    }

    Rule Multiply() {
        return FirstOf(
                Sequence(
                        Subtract(), "* ", Multiply(),
                        push(new MultiplyNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Subtract()
        );
    }

    Rule Subtract() {
        return FirstOf(
                Sequence(
                        Divide(), "- ", Subtract(),
                        push(new SubtractNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Divide()
        );
    }

    Rule Divide() {
        return FirstOf(
                Sequence(
                        Equal(), "/ ", Divide(),
                        push(new DivideNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Equal()
        );
    }

    Rule Equal() {
        return FirstOf(
                Sequence(
                        NotEqual(), "== ", Equal(),
                        push(new EqualNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                NotEqual()
        );
    }

    Rule NotEqual() {
        return FirstOf(
                Sequence(
                        Greater(), "!= ", NotEqual(),
                        push(new NotEqualNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Greater()
        );
    }

    Rule Greater() {
        return FirstOf(
                Sequence(
                        GreaterOrEqual(), "> ", Greater(),
                        push(new GreaterNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                GreaterOrEqual()
        );

    }

    Rule GreaterOrEqual() {
        return FirstOf(
                Sequence(
                        Less(), ">= ", GreaterOrEqual(),
                        push(new GreaterOrEqualNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Less()
        );

    }

    Rule Less() {
        return FirstOf(
                Sequence(
                        LessOrEqual(), "< ", Less(),
                        push(new LessNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                LessOrEqual()
        );

    }

    Rule LessOrEqual() {
        return FirstOf(
                Sequence(
                        Parentheses(), "<= ", LessOrEqual(),
                        push(new LessOrEqualNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Parentheses()
        );

    }

    Rule Parentheses() {
        return FirstOf(
                Sequence("( ", Expression(), ") ", push(new ParenthesesNode((ExpressionNode) pop()))),
                Term()
        );
    }

    Rule Term() {
        return FirstOf(Boolean(), Type(), String(), Number(), Identifier());
    }

    Rule Boolean() {
        return Sequence(
                FirstOf("true", "false"),
                push(new BooleanNode(Boolean.parseBoolean(match()))),
                OptWhiteSpace()
        );
    }

    Rule Type() {
        return Sequence(
                // TODO add variables of Type type support
                FirstOf("String", "Int", "Bool"),
                push(new TypeNode(Type.fromString(match()))),
                OptWhiteSpace()
        );
    }

    Rule Identifier() {
        return Sequence(
                OneOrMore(CharRange('a', 'z')),
                push(new IdentifierNode(match())),
                OptWhiteSpace()
        );
    }

    Rule Number() {
        return Sequence(
                OneOrMore(CharRange('0', '9')),
                push(new NumberNode(Integer.parseInt(match()))),
                OptWhiteSpace()
        );
    }

    Rule String() {
        return Sequence(
                '"',
                ZeroOrMore(
                        FirstOf(
                                Escape(),
                                Sequence(TestNot(AnyOf("\r\n\"\\")), ANY)
                        )
                ).suppressSubnodes(),
                push(new StringNode(match())),
                '"'
        );
    }

    Rule Escape() {
        return Sequence('\\', AnyOf("btnfr\"\'\\"));
    }

    Rule NewLine() {
        return Ch('\n');
    }

    Rule OptWhiteSpace() {
        return ZeroOrMore(AnyOf(" \t"));
    }

}
