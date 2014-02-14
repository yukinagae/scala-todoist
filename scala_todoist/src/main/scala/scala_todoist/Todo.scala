package scala_todoist

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
import todo_macros.MethodInterpolationMacro

class Todo extends Dynamic {

  def applyDynamic(methodName: String)(params: List[(String, String)]): String = macro MethodInterpolationMacro.applyDynamicImpl

}
