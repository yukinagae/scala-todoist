package scala_todoist

import org.apache.http.util.EntityUtils
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.Consts
import org.apache.http.message.BasicNameValuePair
import scala.collection.JavaConversions._

object Core {

  val base = "https://todoist.com/API/"

  var token = "" // TODO

  //  val todo_methods = List("login", "getTimeZones")

  def call(method: String, params: List[(String, String)]): String = {
    val client = HttpClients.createDefault()

    val paramList = params.map(x => new BasicNameValuePair(x._1, x._2))

    val entity = new UrlEncodedFormEntity(paramList)
    val post = new HttpPost(s"http://todoist.com/API/${method}/")
    post.setEntity(entity)
    val response = client.execute(post)

    val resentity = response.getEntity()

    val responseString = EntityUtils.toString(resentity)

    EntityUtils.consume(entity)

    responseString
  }
}
