package routes

import java.io.IOException
import java.net.{ URL, URLClassLoader }
import java.util.jar.{ Attributes, Manifest }

import abct.corporation.tax.api.VersionDetails
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait VersionRoutes {

  lazy val versionRoutes =
    path("version") {
      get {

        val versionInformation: Option[VersionDetails] = readManifest.map { manifest =>
          val mainAttributes: Attributes = manifest.getMainAttributes
          VersionDetails.fromAttributes(mainAttributes)
        }

        import VersionDetails._
        complete(versionInformation.toJson)
      }
    }

  lazy val readManifest: Option[Manifest] = {
    val clazz = getClass
    val className = clazz.getSimpleName + ".class"
    val classPath = clazz.getResource(className).toString
    if (!classPath.startsWith("jar")) {
      None
    } else Some {
      val manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF"
      new Manifest(new URL(manifestPath).openStream())
    }
  }

}
