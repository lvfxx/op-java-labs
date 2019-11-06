package ru.nsu.fit.g16205.semenov.mylangparser

import org.parboiled2._

class MyLanguageParser(val input: ParserInput) extends Parser {

  import ru.nsu.fit.g16205.semenov.mylangparser.MyLanguage._

  def Program: Rule1[StatementNode] = rule {
    Statement ~ EOI
  }

  def Statement: Rule1[StatementNode] = rule {
    Sequence /*~ EOI*/
  }

  def Sequence: Rule1[StatementNode] = rule {
    (SequencedStatement ~ "; " ~ Sequence ~> SequenceNode) | SequencedStatement
  }

  def SequencedStatement: Rule1[SequencedStatementNode] = rule {
    While | Assign | If | DoNothing
  }

  def While = rule {
    "while (" ~ Expression ~ ") { " ~ Statement ~ " }" ~> WhileNode
  }

  def Assign = rule {
    VariableName ~ " = " ~ Expression ~> AssignNode
  }

  def If: Rule1[SequencedStatementNode] = rule {
    "if (" ~ Expression ~ ") { " ~ Statement ~ " } else { " ~ Statement ~ " }" ~> IfNode
  }

  def DoNothing: Rule1[SequencedStatementNode] = rule {
    capture("do-nothing") ~> { (s: String) => DoNothingNode()}
  }

  def ExpressionLine: Rule1[ExpressionNode] = rule {
    Expression ~ EOI
  }

  def Expression: Rule1[ExpressionNode] = rule {
    LessThan
  }

  def LessThan: Rule1[ExpressionNode] = rule {
    (Add ~ " < " ~ LessThan ~> LessThanNode) | Add
  }

  def Add: Rule1[ExpressionNode] = rule {
    (Multiply ~ " + " ~ Add ~> AddNode) | Multiply
  }

  def Multiply: Rule1[ExpressionNode] = rule {
    (Brackets ~ " * " ~ Multiply ~> MultiplyNode) | Brackets
  }

  def Brackets: Rule1[ExpressionNode] = rule {
    ("(" ~ Expression ~ ")" ~> BracketsNode) | Term
  }

  def Term: Rule1[ExpressionNode] = rule {
    Number | Bool | Variable
  }

  def Number: Rule1[NumberNode] = rule {
    capture(oneOrMore(CharPredicate.Digit)) ~> ((s: String) => NumberNode(s.toInt))
  }

  def Bool: Rule1[BoolNode] = rule {
    (capture("true") | capture("false")) ~> ((s: String) => BoolNode(s == "true"))
  }

  def Variable: Rule1[IdentifierNode] = rule {
    VariableName
  }

  def VariableName: Rule1[IdentifierNode] = rule {
    capture(oneOrMore(CharPredicate.LowerAlpha)) ~> IdentifierNode
  }
}
