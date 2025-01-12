# Unisim

## Table of Contents

- [Build](#build)
- [Run](#run)
- [Dependencies](#dependencies)
- [Licenses](#licenses)

## Build

### Prerequisites

This project uses [Maven](https://maven.apache.org), a build automation and project management tool.

- [Download Maven](https://maven.apache.org/download.html)
- [Maven Installation Instructions](https://maven.apache.org/install.html)

### Build process

Once Maven has been installed, the build command can be run from the root directory of the project.

**Compile and Package the project**:

```sh
mvn clean package
```

## Run

Once the project has been compiled. An executable shaded JAR file is produced, which can then be run.

**Run from project root**:

```sh
java -jar ./target/unisim-0.1.0.jar
```

**Run from the same directory as the jar**:

```sh
java -jar unisim-0.1.0.jar
```

To show debug issue press the `Tab` key.

## Dependencies

This project uses the following dependencies:

- JUnit 5 - [JUnit Jupiter API](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api), [JUnit Jupiter Engine](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine), [JUnit Jupiter Params](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-params) - Licensed under the [Eclipse Public License 2.0](https://www.eclipse.org/legal/epl-v20.html)
- LibGDX - [LibGDX](https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx), [LibGDX LWJGL Backend](https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx-backend-lwjgl), [LibGDX Native Libraries](https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx-platform) - Licenses under the [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
- Lombok - [Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok) - Licensed under the [MIT License](https://projectlombok.org/LICENSE)

## Licenses

The assets included in the resources/assets directory are from Icograms. They are licensed under the [Icograms Basic License Agreement](https://education.icograms.com/license-agreement-basic).

The assets included in the resources/iconduck_assets directory are part of the [Carbon Icons](https://iconduck.com/sets/carbon-icons) set from icon duck. They are licensed under the [Apache 2.0 License](https://iconduck.com/licenses/apache-2.0).
