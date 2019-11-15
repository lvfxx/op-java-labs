package ru.nsu.fit.g16205.semenov.jvmlang.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.statements.*;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.AstNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.*;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms.IdentifierNode;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.expressions.terms.NumberNode;

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
        return FirstOf(Assign(), If(), Loop(), Print());
    }

    Rule Print() {
        return Sequence("print ", "( ", Expression(), ") ",
                push(new PrintNode((ExpressionNode) pop()))
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
                        Substract(), "* ", Multiply(),
                        push(new MultiplyNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Substract()
        );
    }

    Rule Substract() {
        return FirstOf(
                Sequence(
                        Divide(), "- ", Substract(),
                        push(new SubstractNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
                ),
                Divide()
        );
    }

    Rule Divide() {
        return FirstOf(
                Sequence(
                        Parentheses(), "/ ", Divide(),
                        push(new DivideNode((ExpressionNode) pop(1), (ExpressionNode) pop()))
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
        return FirstOf(Number(), Identifier());
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

    Rule NewLine() {
        return Ch('\n');
    }

    Rule OptWhiteSpace() {
        return ZeroOrMore(AnyOf(" \t"));
    }

}
