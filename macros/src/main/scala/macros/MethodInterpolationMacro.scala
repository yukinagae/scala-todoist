package macros

import scala.language.experimental.macros
import scala.reflect.macros._

object MethodInterpolationMacro {

  def selectDynamic(name: String): String = macro MethodInterpolationMacro.selectDynamicImpl

  def selectDynamicImpl(c: Context)(name: c.Expr[String]): c.Expr[String] = {
    import c.universe._
    val _name: String = c.eval(c.Expr[String](c.resetAllAttrs(name.tree.duplicate)))
    if (_name.matches("^[A-Z0-9].+")) {
      // compilation error!
      c.error(c.enclosingPosition, s"${_name} should be uncapitalized!")
    }
    reify(name.splice.toUpperCase)
  }

}
