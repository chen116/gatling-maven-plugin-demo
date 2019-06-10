package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import org.fusesource.mqtt.client.QoS
import scala.concurrent.duration._
import com.github.mnogu.gatling.mqtt.Predef._


class BasicSimulation extends Simulation {


  val mqttConfiguration = mqtt.host("tcp://localhost:1883")
    // MQTT broker
//    .host("tcp://localhost:1883")
  val connect = exec(mqtt("connect")
    .connect())
  // send 100 publish MQTT messages
  val publish = repeat(100) {
    exec(mqtt("publish")
      // topic: "foo"
      // payload: "Hello"
      // QoS: AT_LEAST_ONCE (1)
      // retain: false
      .publish("foo", "Hello", QoS.AT_LEAST_ONCE, retain = false))
      // 1 seconds pause between sending messages
      .pause(1000 milliseconds)
  }
  val disconnect = exec(mqtt("disconnect")
    .disconnect())
  val scn = scenario("MQTT Test")
    .exec(connect, publish, disconnect)
  setUp(scn
    // linearly connect 10 devices over 1 second
    // and send 100 publish messages
    .inject(rampUsers(10) over (1 seconds))
  ).protocols(mqttConfiguration)
//  val httpProtocol = http
//    .baseUrl("http://computer-database.gatling.io") // Here is the root for all relative URLs
//    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
//    .acceptEncodingHeader("gzip, deflate")
//    .acceptLanguageHeader("en-US,en;q=0.5")
//    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
//
//  val scn = scenario("Scenario Name") // A scenario is a chain of requests and pauses
//    .exec(http("request_1")
//      .get("/"))
//    .pause(7) // Note that Gatling has recorder real time pauses
//    .exec(http("request_2")
//      .get("/computers?f=macbook"))
//    .pause(2)
//    .exec(http("request_3")
//      .get("/computers/6"))
//    .pause(3)
//    .exec(http("request_4")
//      .get("/"))
//    .pause(2)
//    .exec(http("request_5")
//      .get("/computers?p=1"))
//    .pause(670 milliseconds)
//    .exec(http("request_6")
//      .get("/computers?p=2"))
//    .pause(629 milliseconds)
//    .exec(http("request_7")
//      .get("/computers?p=3"))
//    .pause(734 milliseconds)
//    .exec(http("request_8")
//      .get("/computers?p=4"))
//    .pause(5)
//    .exec(http("request_9")
//      .get("/computers/new"))
//    .pause(1)
//    .exec(http("request_10") // Here's an example of a POST request
//      .post("/computers")
//      .formParam("""name""", """Beautiful Computer""") // Note the triple double quotes: used in Scala for protecting a whole chain of characters (no need for backslash)
//      .formParam("""introduced""", """2012-05-30""")
//      .formParam("""discontinued""", """""")
//      .formParam("""company""", """37"""))
//
//  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
