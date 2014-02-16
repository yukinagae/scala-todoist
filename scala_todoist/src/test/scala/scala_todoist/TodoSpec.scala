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

class TodoSpec extends Specification {

  "scala-todoist" should {
    "login" in {

      // you can create each Todo instance.
      val todo1 = new Todo()
      val todo2 = new Todo()

      println(todo1.login(List(("email", "12345@gmail.com"), ("password", "12345"))))
      println(todo1.getProjects(Nil))

      println(todo2.login(List(("email", "notExist@gmail.com"), ("password", "notExist"))))
      println(todo2.getProjects(Nil))

      println(todo1.getProjects(Nil))

      success
    }
  }

}
