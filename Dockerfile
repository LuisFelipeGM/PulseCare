# syntax=docker/dockerfile:1.7

ARG JAVA_VERSION=21

FROM eclipse-temurin:${JAVA_VERSION}-jdk-jammy AS builder

WORKDIR /workspace

COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle ./
COPY buildSrc buildSrc

RUN chmod +x gradlew

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon help || true

COPY pulse-care-core pulse-care-core
COPY pulse-care-agendamento-service pulse-care-agendamento-service
COPY pulse-care-notificacao-service pulse-care-notificacao-service
COPY pulse-care-historico-service pulse-care-historico-service

ARG SERVICE_MODULE

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon :${SERVICE_MODULE}:bootJar -x test

###

FROM eclipse-temurin:${JAVA_VERSION}-jre-jammy

ARG SERVICE_MODULE

WORKDIR /app

RUN groupadd --system spring && \
    useradd --system --gid spring --uid 1001 --create-home spring

COPY --from=builder /workspace/${SERVICE_MODULE}/build/libs/*.jar /app/app.jar

RUN chown -R spring:spring /app

USER spring

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
