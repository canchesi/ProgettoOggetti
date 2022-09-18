FROM maven:3.8.6-openjdk-18

ENV TELEGRAM_TOKEN=""
ENV MONGO_URL=127.0.0.1:27017
ENV MONGO_USER=""
ENV MONGO_PASS=""
ENV MONGO_EXTRA=""
ENV MONGO_PREFIX=""
ENV BOTNAME=""

COPY . /usr/src/GallettaBot

WORKDIR /usr/src/GallettaBot

RUN mvn clean install

ENTRYPOINT ["/bin/bash", "-c", "./entrypoint.sh"]
