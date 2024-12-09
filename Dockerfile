FROM openjdk:23-jdk-oracle as builder

#LABEL maintainer="jojoyoung"

ARG COMPILED_DIR=/compiledir

WORKDIR ${COMPILED_DIR}

COPY src src
COPY .mvn .mvn
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .

RUN ./mvnw package -Dmaven.test.skip=true

ENV SERVER_PORT=3000
ENV SPRING_DATA_REDIS_HOST=localhost
ENV SPRING_DATA_REDIS_USERNAME=""
ENV SPRING_DATA_REDIS_PASSWORD=""
ENV SPRING_DATA_REDIS_PORT=6379
ENV SPRING_DATA_REDIS_DATABASE=0

EXPOSE ${SERVER_PORT}

#ENTRYPOINT java -jar target/Practice-Test-0.0.1-SNAPSHOT.jar

#second stage
FROM openjdk:23-jdk-oracle

ARG WORK_DIR=/app

WORKDIR ${WORK_DIR}

COPY --from=builder /compiledir/target/Practice-Test-0.0.1-SNAPSHOT.jar Practice-Test-2.0.jar

ENV SERVER_PORT=3000

ENV SPRING_DATA_REDIS_HOST=localhost
ENV SPRING_DATA_REDIS_USERNAME=""
ENV SPRING_DATA_REDIS_PASSWORD=""
ENV SPRING_DATA_REDIS_PORT=6379
ENV SPRING_DATA_REDIS_DATABASE=0

EXPOSE ${SERVER_PORT}

ENTRYPOINT java -jar Practice-Test-2.0.jar

#HEALTHCHECK --interval=30s --timeout=30s --start-period=5s

#example of healthcheck, the line after CMD is an example, not fixed
#HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 CMD curl -f http://localhost:5000/health || exit 1


#-e SPRING_DATA_REDIS_HOST=junction.proxy.rlwy.net
#-e SPRING_DATA_REDIS_USERNAME=default
#-e SPRING_DATA_REDIS_PASSWORD=HvlqUOwexFtBKSRONAvqXRAeuvCnJxod
#-e SPRING_DATA_REDIS_PORT=12122
#-e SPRING_DATA_REDIS_DATABASE=0
