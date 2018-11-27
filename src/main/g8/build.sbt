import ReleaseTransformations._

lazy val akkaHttpVersion = "$akka_http_version$"
lazy val akkaVersion    = "$akka_version$"

enablePlugins(BuildInfoPlugin)

enablePlugins(GitBranchPrompt)

enablePlugins(DockerPlugin)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "$organisation$",
      scalaVersion    := "$scala_version$"
    )),
    name := "$name$",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"         % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-xml"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"       % akkaVersion,
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "org.scalatest"     %% "scalatest"         % "3.0.5"         % Test
    ),
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "$package$.build"
  )

dockerAutoPackageJavaApplication(fromImage = "java:8", exposedPorts = Seq($server_port$))

git.useGitDescribe := true

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  releaseStepCommand("dockerBuildAndPush"),
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)

packageOptions in (Compile, packageBin) +=  {
  import java.util.jar.{Manifest}
  import java.util.jar.Attributes.Name
  val manifest = new Manifest
  val mainAttributes = manifest.getMainAttributes()
  mainAttributes.put(new Name("Git-Version"), git.gitDescribedVersion.value.getOrElse("Unknown-git-version"))
  mainAttributes.put(new Name("Git-Uncommitted-Changes"), git.gitUncommittedChanges.value.toString)
  Package.JarManifest( manifest )
}

buildInfoKeys ++= Seq[BuildInfoKey](
  "applicationOwner" -> organization.value,
  BuildInfoKey.action("buildTime") { System.currentTimeMillis },
  BuildInfoKey.action("gitVersion") { git.gitDescribedVersion.value.getOrElse("Unknown-git-version") },
  BuildInfoKey.action("releasedVersion") { git.gitUncommittedChanges.value.toString }
)

buildInfoOptions += BuildInfoOption.ToJson

imageNames in docker := Seq(
  ImageName(s"\${organization.value}/\${name.value}:latest"),
  {
    val baseVersion = version.value
    val actualVersion = if (baseVersion.endsWith("-SNAPSHOT")) baseVersion else "v" + baseVersion
    ImageName(
      namespace = Some(organization.value),
      repository = name.value,
      tag = Some(actualVersion)
    )
  }
)