package todo_macros

import scala.language.experimental.macros
import scala.reflect.macros._

import scala.language.dynamics

/**
 * macro that checks the called method is defined in todo_methods
 */
object MethodInterpolationMacro {

  def applyDynamicImpl(c: Context)(methodName: c.Expr[String])(params: c.Expr[List[(String, String)]]): c.Expr[String] = {
    import c.universe._
    val _name: String = c.eval(c.Expr[String](c.resetAllAttrs(methodName.tree.duplicate)))
    // check method name
    if (!todo_methods.contains(_name)) {
      c.error(c.enclosingPosition, s"${_name} is not implemented.")
    }
    // call the API
    reify((c.Expr[Core](c.prefix.tree)).splice.call(methodName.splice, params.splice))
  }

  /**
   * available methods
   */
  val todo_methods = List( //
    "login", //
    "getTimezones", //
    "register", //
    "updateUser", //
    "getProjects", //
    "getProject", //
    "addProject", //
    "updateProject", //
    "updateProjectOrders", //
    "deleteProject", //
    "archiveProject", //
    "unarchiveProject", //
    "getLabels", //
    "addLabel", //
    "updateLabel", //
    "updateLabelColor", //
    "deleteLabel", //
    "getUncompletedItems", //
    "getAllCompletedItems", //
    "getCompletedItems", //
    "getItemsById", //
    "addItem", //
    "updateItem", //
    "updateOrders", //
    "moveItems", //
    "updateRecurringDate", //
    "deleteItems", //
    "completeItems", //
    "uncompleteItems", //
    "addNote", //
    "updateNote", //
    "deleteNote", //
    "getNotes", //
    "query" //
    )
}

/**
 * Todo class
 */
trait Core extends Dynamic {

  def applyDynamic(methodName: String)(params: List[(String, String)]): String = macro MethodInterpolationMacro.applyDynamicImpl
  //  def applyDynamic(methodName: String)(params: List[(String, String)]): String = call(methodName, params)

  /**
   * call the API with JSON parameter
   */
  def call(method: String, params: List[(String, String)]): String
}
