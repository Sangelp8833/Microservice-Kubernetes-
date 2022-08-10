FROM openjdk:17.0.1

WORKDIR /app/msvc-usuarios

COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]