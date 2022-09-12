FROM maven:3.8.6-openjdk-18

COPY . /usr/src/GallettaBot
WORKDIR /usr/src/GallettaBot

RUN mvn clean install

WORKDIR /usr/src/GallettaBot/target
ENTRYPOINT ["java", "-jar", "GallettaBot.jar"]
