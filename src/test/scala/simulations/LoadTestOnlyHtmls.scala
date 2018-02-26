package simulations

import java.io.File
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import com.typesafe.config._
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import io.gatling.http.action.sync.HttpRequestActionBuilder
import io.gatling.core.structure.ChainBuilder
import scala.Vector

class LoadTestOnlyHtmls extends Simulation {

  /* get maven args. and assign to vars */
  val env = System.getProperty("env")

  val userPerSec:Int=System.getProperty("userPerSec").toInt
  val duration:Int=System.getProperty("duration").toInt

  /* Pretty print to console using interpolation*/
  println(s"userPerSec :  $userPerSec")
  println(s"duration :  $duration")
  println(s"environment :  $env")

  
  /* based on argument given, e.g. if QA is passed to maven then load qa.conf. Then assign to conf variable */
  val parsedConfig = ConfigFactory.parseFile(new File("src/test/resources/" + env + ".conf"))
  val conf = ConfigFactory.load(parsedConfig)
  
  var baseUrl = conf.getString("load.dispatcherUrl") + conf.getString("load.basePath") 

  println(s"domain :  $baseUrl")
		  
  val locales = jsonFile("locales.json")
  val pages = jsonFile("pages.json")
  var count = 0;
  var httpLists = new ListBuffer[ChainBuilder]()
  for (localeNum <- 0 to locales.records.size -1) { 
	  for (pageNum <- 0 to pages.records.size -1) { 
		  count +=1
		  val path = ""+locales.records(localeNum)("locale") + pages.records(pageNum)("page")+ "?skipCache=true"
		  println(s"url$count :  $path")
		  httpLists += exec(http("Page: " + pages.records(pageNum)("page")).get(path))
	  }
	}
  val users = (userPerSec * duration) / (locales.records.size * pages.records.size)
  println(s"users :  $users")
  val scnItems = scenario("My Scenari").exec(httpLists.toList)
  


   val httpProtocol = http
    .baseURL(baseUrl).inferHtmlResources(WhiteList(""".*\.html""")).disableCaching
    
  val execution = scnItems
    .inject(rampUsers(users) over duration)
  setUp(execution).protocols(httpProtocol).assertions(
    global.responseTime.max.lt(5000),
    global.responseTime.mean.lt(800),
    global.successfulRequests.percent.gt(95))

}