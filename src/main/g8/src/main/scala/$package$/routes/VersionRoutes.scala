package $package$.routes

import $package$.build.BuildInfo
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json._

trait VersionRoutes {

  lazy val versionRoutes =
    path("version") {
      get {
        complete(BuildInfo.toJson.parseJson)
      }
    }

}
