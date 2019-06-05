FROM openjdk:8
ADD build/libs/prova-votacao-0.0.1-SNAPSHOT.jar /
EXPOSE 8282
ENTRYPOINT ["java", "-jar", "prova-votacao-0.0.1-SNAPSHOT.jar"]