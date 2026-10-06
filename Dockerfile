FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /myapp

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B dependency:go-offline

#1: Maven stores downloaded dependencies under: /root/.m2
    # So if you rebuild tomorrow, Docker can reuse Maven artifacts 
    # rather than downloading everything again. This is a build-time cache.

#2:  mvn -B means: `batch mode`
    # It's useful in CI/CD because Maven won't try to interactively ask questions.
    # You want deterministic, non-interactive builds.

#3: mvn dependency:go-offline means: 
    # This asks Maven to download dependencies required for the project 
    #ahead of the actual build.

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -DskipTests package
# 4: Why -DskipTests? It means: 
    # Build the application without executing tests.
    # For a Docker build this can make sense if your CI pipeline already runs tests separately.
# 5: If no -DskipTests then: 
    # `mvn package` --> this build executable jar file

FROM eclipse-temurin:21-jre-alpine

WORKDIR /myapp

# Create a system group named appgroup, 
# then create a system user named appuser and put that user in appgroup
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder --chown=appuser:appgroup \
    /myapp/target/*.jar \
    ./shopivo-product-service.jar

USER appuser:appgroup

EXPOSE 5002

# MaxRAMPercentage: Don't assume it can consume unlimited memory; 
# configure the maximum heap based on the container's available memory.
    # For example, if the container has a 1 GB memory limit, approximately:
    # `1 GB × 75% ≈ 768 MB` can be used as the JVM maximum heap.

# InitialRAMPercentage: This tells the JVM to start with an initial heap sized 
# as a percentage of the available container memory.

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-XX:InitialRAMPercentage=25.0", "-jar", "shopivo-product-service.jar"]