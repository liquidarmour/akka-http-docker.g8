A [Giter8][g8] template for a Akka HTTP application with Docker, release, git and versioning built in!

The aim of this is to provide a base application that can be released and managed almost completely in SBT. 

This project began as project based on [akka-http-scala-seed], so thanks to Lightbend for providing something solid to start on.  

The application uses multiple [sbt] plugins including:
 * [sbt-release] to manage the release of the application
 * [sbt-git] to extract git information to help with versioning
 * [sbt-buildInfo] to allow the application to expose its own version information
 * [sbt-docker] to build and push docker images to run this application

Prerequisites:

JDK 8

sbt 0.13.13 or higher

Open a console and run the following command to apply this template:

sbt new liquidarmour/akka-http-docker.g8

This template will prompt for the following parameters. Press Enter if the default values suit you:

- name: Becomes the name of the project.
- package: Becomes the route package for the scala code of the project.
- description: Provides a description for the project.
- scala_version: Specifies the Scala version for this project
- akka_http_version: Specifies which version of Akka HTTP should be used for this project.
- akka_version: Specifies which version of Akka should be used for this project.
- github_id: the github user id for this project
- developer_url: The developer URL
- project_url: The github project URL
- server_port: The port you want your app to run when run from SBT

WebServerHttpApp: This server is started using the recently introduced HttpApp, which bootstraps a server with just a few lines. For this particular case, the routes are defined in the same class, but you can also define them in separated traits or objects.

Running in SBT
--------------
**sbt run**

This will run the server on localhost. You can navigate to *http://localhost:9000/version* to see the application version information.

**sbt docker**

This will build a docker container image and publish it locally.

**sbt release**

This will follow the normal release for an sbt managed library except it will publish the release to Docker Hub as a ready to run docker image.

Template license
----------------
Written in 2017 by James Williams james@liquid-armour.co.uk

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
[sbt-release]: https://github.com/sbt/sbt-release
[sbt-git]: https://github.com/sbt/sbt-git
[sbt-buildInfo]: https://github.com/sbt/sbt-buildinfo
[sbt-docker]: https://github.com/marcuslonnberg/sbt-docker
[akka-http-scala-seed]: https://github.com/akka/akka-http-scala-seed.g8
[sbt]: http://www.scala-sbt.org/
