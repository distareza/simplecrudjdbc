FROM openjdk:8
ADD target/simplecrudjdbc-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ "java", "-jar", "simplecrudjdbc-0.0.1-SNAPSHOT.jar" ]