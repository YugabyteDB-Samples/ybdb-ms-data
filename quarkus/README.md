[![Quarkus CI with Gradle](../../../actions/workflows/gradle-quarkus.yml/badge.svg?branch=main)](../../../actions/workflows/gradle-quarkus.yml)

[Quarkus](https://quarkus.io/) is a Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards.

This describes how to build a simple JPA based web application using Quarkus framework for YSQL API using [Yugabyte JDBC Driver](https://docs.yugabyte.com/latest/integrations/jdbc-driver/).

## Prerequisites

- Follow [YB Quick start](https://docs.yugabyte.com/latest/quick-start/) instructions to run a local YugabyteDB cluster. Test YugabyteDB's YSQL API, as [documented](../../quick-start/explore/ysql/) so that you can confirm that you have YSQL service running on `localhost:5433`.
- You will need JDK 11 or above. You can use [SDKMAN](https://sdkman.io/install) to install the JDK runtime.

## Get Started

You can find the complete source at [java framework with smart driver for YSQL](https://github.com/yugabyte/yb-ms-data.git). This project has directories for different java frameworks such as spring-boot, quarkus and micronaut. Clone this repository to a local workstation and open the `yb-ms-data` directory in your favorite IDE to easily navigate and explore Quarkus's project files.

```sh
git clone [REPO]
```

## Dependencies

This project depends on the following libraries.
```gradle
implementation("io.quarkus:quarkus-hibernate-orm")
implementation("io.quarkus:quarkus-flyway")
implementation("io.quarkus:quarkus-resteasy")
implementation("io.quarkus:quarkus-resteasy-jackson")
implementation("io.quarkus:quarkus-config-yaml")
implementation("io.quarkus:quarkus-agroal")
implementation("io.quarkus:quarkus-smallrye-fault-tolerance")
implementation("com.yugabyte:jdbc-yugabytedb:42.3.5-yb-1")
```
Update the driver dependency library **("com.yugabyte:jdbc-yugabytedb")** to the latest version. Grab the latest version from [Yugabyte JDBC driver](https://docs.yugabyte.com/latest/integrations/jdbc-driver/).

## Driver Configuration

Refer to the file `yb-ms-data/quarkus/src/main/resources/application.yaml` in the project directory:

```yml
quarkus:
  datasource:
    db-kind: pgsql
    jdbc:
      url: jdbc:yugabytedb://[hostname:port]/yugabyte
      driver: com.yugabyte.Driver
      initial-size: 5
      max-size: 20
      additional-jdbc-properties:
        load-balance: true
```

- **db-kind** indicates the type of the db instance. The value can be *pqsql* or *postgresql* for Postgres or Postgres API compatible instances. As YugabyteDB reuses Postgres Query layer, you can have one of these values.
- **url** is the JDBC connection string.
- **driver** is the JDBC driver class name.
- **additional-jdbc-properties** is where YugabyteDB driver specific properties such as `load-balance` and `topology-keys` can be set.

Update the JDBC url with the appropriate `hostname` and `port` number details `jdbc:yugabytedb://[hostname:port]/yugabyte` in the application.yaml file. Remember to remove the square brackets. It is just a place holder to indicate the fields that need user inputs.

## Normal build and run

Navigate to `yb-ms-data/quarkus` folder in the project:

```sh
cd yb-ms-data/quarkus
```

To build the app:

```sh
gradle quarkusBuild
```

To build the app with `ysql` profile:

```sh
gradle -Dquarkus.profile=ysql quarkusBuild
```

To run & test the app:

```sh
gradle quarkusDev
```

To run the app with `ysql` profile:
```sh
gradle -Dquarkus.profile=ysql quarkusDev
```

## Native build and run (GraalVM: 20.0.1-graalce)

Navigate to `yb-ms-data/quarkus` folder in the project:

```sh
cd yb-ms-data/quarkus
```

To build the app with `ysql` profile:

```sh
gradle -Dquarkus.profile=ysql -Dquarkus.package.type=native build 
```

To run the app with `ysql` profile:
```sh
 ./build/yb-quarkus-data-1.0.0-runner -Dquarkus.profile=ysql
```