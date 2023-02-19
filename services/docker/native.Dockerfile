# Using multistage docker build

# Cache layer container for gradle dependencies
# Will be rebuilt only if changes are made to gradle files
FROM gradle:jdk19-focal AS CACHE
ARG module_name
# Setting up gradle cache directory
RUN mkdir /gradle_cache
ENV GRADLE_USER_HOME /gradle_cache
WORKDIR /workspace
# Using parent gradle files
COPY build.gradle.kts ./
COPY settings.gradle.kts ./
# Using service gradle files
WORKDIR /workspace/${module_name}
COPY ${module_name}/build.gradle.kts ./
# Fetching dependencies
RUN gradle dependencies --no-daemon --stacktrace

# Build layer container for gradle build
FROM gradle:jdk19-focal AS BUILDER
ARG module_name
# Install GraalVM
RUN apt-get update && \
    apt-get install -y wget gcc libz-dev && \
    rm -rf /var/lib/apt/lists/*
ARG VERSION=22.3.1
RUN wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-$VERSION/graalvm-ce-java19-linux-amd64-$VERSION.tar.gz && \
    tar -xvzf graalvm-ce-java19-linux-amd64-$VERSION.tar.gz && \
    rm graalvm-ce-java19-linux-amd64-$VERSION.tar.gz && \
RUN /graalvm-ce-$VERSION/bin/gu install native-image
ENV PATH="/graalvm-ce-$VERSION/bin:${PATH}"
WORKDIR /workspace
# Re-using gradle cache
COPY --from=CACHE /gradle_cache /home/gradle/.gradle
# Using parent gradle files
COPY build.gradle.kts ./
COPY settings.gradle.kts ./
# Copying service files
WORKDIR /workspace/${module_name}
COPY ${module_name}/build.gradle.kts ./
COPY ${module_name}/src/ ./src/
# Building service jar
RUN gradle nativeCompile --no-daemon --stacktrace
# Copy artifact
RUN rm build/libs/*-plain.jar; \
    mv build/libs/*.jar /service.jar

# Build native image using GraalVM
FROM ghcr.io/graalvm/graalvm-ce:22.3.1 AS WTF
WORKDIR /workspace
RUN gu install native-image
COPY --from=BUILDER /service.jar ./
RUN native-image -jar service.jar \
    --no-fallback \
    --static \
    --enable-monitoring \
    -H:Name=service
RUN mv service /service


# Build final native image
FROM scratch AS FINAL
WORKDIR /workspace
COPY --from=WTF /service ./
CMD ./service
