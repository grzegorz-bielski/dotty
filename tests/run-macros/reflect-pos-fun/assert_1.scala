import scala.quoted._

object scalatest {

  inline def assert(condition: => Boolean): Unit = ${ assertImpl('condition) }

  def assertImpl(cond: Expr[Boolean])(using qctx: QuoteContext) : Expr[Unit] = {
    import qctx.reflect._
    import util._

    Term.of(cond).underlyingArgument match {
      case t @ Apply(TypeApply(Select(lhs, op), targs), rhs) =>
        ValDef.let(lhs) { left =>
          ValDef.let(rhs) { rs =>
            val app = Select.overloaded(left, op, targs.map(_.tpe), rs)
            val b = app.asExprOf[Boolean]
            Term.of('{ scala.Predef.assert($b) })
          }
        }.asExprOf[Unit]
    }
  }
}
