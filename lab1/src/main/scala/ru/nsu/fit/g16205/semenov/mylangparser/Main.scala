package ru.nsu.fit.g16205.semenov.mylangparser

object Main extends App {
  val text = """a = 5; b = 10"""
  val parsingResult = new MyLanguageParser(text).Program.run()
  print(parsingResult.isSuccess)
}
