package scala_todoist

import org.specs2.mutable._
import org.apache.http.impl.client.HttpClients
import sun.security.jgss.HttpCaller
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.message.BasicNameValuePair
import org.apache.http.Consts
import scala_todoist.{ Core => Todo }

class CoreSpec extends Specification {

  "scala-todoist" should {
    "login" in {

      val todo = new Todo()

      println(todo.login(List(("email", "12345@gmail.com"), ("password", "12345"))))
      //      println(todo.getProjects(Nil))
      //      println(todo.addItem(List(("project_id", "121366566"), ("content", "test item"))))

      success
    }
  }

}
