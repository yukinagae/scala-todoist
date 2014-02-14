package core

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

      val params = List(("email", "12345@gmail.com"), ("password", "12345"))

      val todo = new Todo()

      println(todo.login(params))
      println(todo.getProjects(Nil))
      println(todo.hoge(Nil))

      success
    }
  }

}
