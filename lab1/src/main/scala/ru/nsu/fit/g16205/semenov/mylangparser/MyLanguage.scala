package ru.nsu.fit.g16205.semenov.mylangparser

object MyLanguage {

  sealed abstract class AstNode

  sealed trait StatementNode extends AstNode

  case class SequenceNode(left: StatementNode, right: StatementNode) extends StatementNode

  sealed trait SequencedStatementNode extends StatementNode

  case class WhileNode(condition: ExpressionNode, body: StatementNode) extends SequencedStatementNode

  case class AssignNode(variableName: IdentifierNode, value: ExpressionNode) extends SequencedStatementNode

  case class IfNode(condition: ExpressionNode, ifTrue: StatementNode, ifFalse: StatementNode) extends SequencedStatementNode

  case class DoNothingNode() extends SequencedStatementNode

  sealed trait ExpressionNode extends AstNode

  case class IdentifierNode(name: String) extends ExpressionNode

  case class LessThanNode(left: ExpressionNode, right: ExpressionNode) extends ExpressionNode

  case class AddNode(left: ExpressionNode, right: ExpressionNode) extends ExpressionNode

  case class MultiplyNode(left: ExpressionNode, right: ExpressionNode) extends ExpressionNode

  case class BracketsNode(value: ExpressionNode) extends ExpressionNode

  sealed trait TermNode extends ExpressionNode

  case class NumberNode(number: Int) extends TermNode

  case class BoolNode(bool: Boolean) extends TermNode

  case class StringNode(string: String) extends TermNode

}

