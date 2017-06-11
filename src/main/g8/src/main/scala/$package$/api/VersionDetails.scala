package $package$.api

import java.util.jar.Attributes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

case class VersionDetails(
  applicationName: String,
  applicationOwner: String,
  applicationVersion: String,
  gitVersion: String,
  releasedVersion: Boolean
)

object VersionDetails extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val VersionDetailsFormat = jsonFormat5(VersionDetails.apply)

  def fromAttributes(attirbutes: Attributes): VersionDetails = {
    val version = attirbutes.getValue("Implementation-Version")
    val gitVersion = attirbutes.getValue("Git-Version")
    VersionDetails(
      applicationName = attirbutes.getValue("Implementation-Title"),
      applicationOwner = attirbutes.getValue("Specification-Vendor"),
      applicationVersion = version,
      gitVersion = gitVersion,
      releasedVersion = gitVersion == version
    )
  }
}