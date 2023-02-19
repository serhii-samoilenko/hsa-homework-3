# Using multistage docker build

# Cache layer container for gradle dependencies
# Will be rebuilt only if changes are made to gradle files
FROM gradle:jdk19-focal AS CACHE
ARG MODULE_NAME
# Setting up gradle cache directory
RUN mkdir /gradle_cache
ENV GRADLE_USER_HOME /gradle_cache
WORKDIR /workspace
# Using parent gradle files
COPY build.gradle.kts ./
COPY settings.gradle.kts ./
# Using service gradle files
WORKDIR /workspace/${MODULE_NAME}
COPY ${MODULE_NAME}/build.gradle.kts ./
# Fetching dependencies
RUN gradle dependencies --no-daemon --stacktrace

# Build layer container for gradle build
FROM gradle:jdk19-focal AS BUILDER
ARG MODULE_NAME
WORKDIR /workspace
# Re-using gradle cache
COPY --from=CACHE /gradle_cache /home/gradle/.gradle
# Using parent gradle files
COPY build.gradle.kts ./
COPY settings.gradle.kts ./
# Copying service files
WORKDIR /workspace/${MODULE_NAME}
COPY ${MODULE_NAME}/build.gradle.kts ./
COPY ${MODULE_NAME}/src/ ./src/
# Building service jar
RUN gradle build -x test --no-daemon --stacktrace
# Copy artifact
RUN rm build/libs/*-plain.jar; \
    mv build/libs/*.jar /service.jar

# Build final image
# gradle:jdk19-focal
# openjdk:19-jdk-alpine
# corretto:19-alpine
# bellsoft/liberica-openjdk-alpine-musl:19
FROM bellsoft/liberica-openjdk-alpine-musl:19 AS FINAL
WORKDIR /workspace
COPY --from=BUILDER /service.jar ./
CMD java -jar service.jar
