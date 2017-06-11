package $package$

import akka.http.scaladsl.server.{ HttpApp, Route }
import $package$.routes.VersionRoutes
/**
 * Server will be started calling `WebServerHttpApp.startServer("localhost", 8080)`
 * and it will be shutdown after pressing return.
 */
object WebServerHttpApp extends HttpApp with App with VersionRoutes {
  // Routes that this WebServer must handle are defined here
  // Please note this method was named `route` in versions prior to 10.0.7
  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete("Server up and running") // Completes with some text
    } ~ versionRoutes

  // This will start the server until the return key is pressed
  startServer("0.0.0.0", 9000)
}
