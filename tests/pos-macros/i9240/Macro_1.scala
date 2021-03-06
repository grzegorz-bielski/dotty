import scala.quoted._

inline def diveInto[T]: String = ${ diveIntoImpl[T]() }

def diveIntoImpl[T]()(implicit qctx: QuoteContext, ttype: Type[T]): Expr[String] =
  import qctx.reflect._
  Expr( unwindType(TypeRepr.of[T]) )

def unwindType(using QuoteContext)(aType: qctx.reflect.TypeRepr): String =
  import qctx.reflect._

  aType match {
    case AppliedType(t,tob) =>
      val cs = t.classSymbol.get.primaryConstructor  // this is shared
      val a = cs.paramSymss  // this call succeeds
      // println("a: "+a)
      val b = cs.paramSymss // this call explodes
      // println("b: "+b)

    case _ =>
  }
  "OK!"

