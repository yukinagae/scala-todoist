package scala_todoist

import scala.collection.JavaConversions.seqAsJavaList

import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

import scala.util.parsing.json.JSON

class Core {

  val base = "https://todoist.com/API/"

  var token: String = ""

  def login(params: List[(String, String)]): String = call("login", params)
  def getTimeZones(params: List[(String, String)]): String = call("getTimeZones", params)
  def getProjects(params: List[(String, String)]): String = call("getProjects", params)

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
        case None =>
      }
    }

    EntityUtils.consume(entity)

    responseString
  }
}
