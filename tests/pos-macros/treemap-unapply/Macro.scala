import scala.quoted._

inline def mcr(x: => Unit): Unit = ${mcrImpl('x)}
def mcrImpl(x: Expr[Unit])(using QuoteContext) : Expr[Unit] =
  import qctx.reflect._
  val tr: Term = Term.of(x)
  object m extends TreeMap
  m.transformTerm(tr).asExprOf[Unit]
