package com.shionfujie.lexicor.core.domain

sealed trait Lexeme {

  val pos: Pos

  def repr: String

}

sealed trait Subject extends Lexeme

sealed trait Target extends Lexeme {

  val value: String

}

sealed trait Keyword extends Lexeme {

  val keyword: Symbol

}

object Lexemes {

  def Keyword(pos: Pos, keyword: Symbol): Keyword = KeywordImpl(pos, keyword)

  def Subject(pos: Pos, keyword: Symbol): Subject = SubjectImpl(pos, keyword)

  object Keyword {

    def unapply(lexeme: Lexeme): Option[(Pos, Symbol)] = lexeme match {
      case KeywordImpl(pos, keyword) => Some((pos, keyword))
      case _                         => None
    }

  }

  object Subject {

    def unapply(lexeme: Lexeme): Option[(Pos, Symbol)] = lexeme match {
      case SubjectImpl(pos, keyword) => Some((pos, keyword))
      case _                         => None
    }

  }

  case class TagLiteral(pos: Pos, path: List[String]) extends Target {

    override def repr: String = Representations.TagLiteral

    override val value: String = path.mkString("/")

  }

  case class Unknown(pos: Pos) extends Lexeme {

    override def repr: String = Representations.Unknown

  }

  private case class KeywordImpl(pos: Pos, keyword: Symbol) extends Keyword {

    override def repr: String = Representations.Keyword

  }

  private case class SubjectImpl(pos: Pos, keyword: Symbol) extends Keyword with Subject {

    override def repr: String = Representations.Keyword

  }

}
