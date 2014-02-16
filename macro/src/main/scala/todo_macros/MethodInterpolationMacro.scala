package todo_macros

import scala.collection.JavaConversions.seqAsJavaList

import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

import scala.util.parsing.json.JSON
import scala.language.dynamics

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
    reify((c.Expr[Todo](c.prefix.tree)).splice.call(methodName.splice, params.splice))
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
trait Todo extends Dynamic {

  def applyDynamic(methodName: String)(params: List[(String, String)]): String = macro MethodInterpolationMacro.applyDynamicImpl

  /**
   * store token, gained after login
   */
  var token: String = ""

  /**
   * base URL of the API
   */
  val base = "https://todoist.com/API/"

  /**
   * call the API with JSON parameter
   */
  def call(method: String, params: List[(String, String)]): String = {
    val client = HttpClients.createDefault()

    val paramList = params.map(x => new BasicNameValuePair(x._1, x._2))

    val entity = new UrlEncodedFormEntity(new BasicNameValuePair("token", token) :: paramList)
    val post = new HttpPost(s"${base}${method}")
    post.setEntity(entity)
    val response = client.execute(post)

    val resentity = response.getEntity()

    val responseString = EntityUtils.toString(resentity)

    if (method == "login") {
      JSON.parseFull(responseString) match {
        case Some(v) =>
          val map: Map[String, Option[Any]] = v.asInstanceOf[Map[String, Option[Any]]]
          token = map.getOrElse("token", "").toString
        case None => token = ""
      }
    }

    EntityUtils.consume(resentity)

    responseString
  }
}
