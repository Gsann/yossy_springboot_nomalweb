FROM maven:3.6.3-openjdk-11-slim as build

WORKDIR /src

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
# ADD pom.xml .
# RUN mvn package -Dmaven.test.skip -Declipselink.weave.skip