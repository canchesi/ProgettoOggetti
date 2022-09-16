FROM maven:3.8.6-openjdk-18

ENV TELEGRAM_TOKEN=5520286465:AAGfEVNeE4g5cdhIMVBKNhhXXZ8AarzYU-U
ENV MONGO_URL=127.0.0.1:27017
ENV MONGO_USER=root
ENV MONGO_PASS=root
ENV MONGO_EXTRA=""
ENV MONGO_PREFIX=""

COPY . /usr/src/GallettaBot

WORKDIR /usr/src/GallettaBot

RUN mvn clean install

WORKDIR /usr/src/GallettaBot/target
ENTRYPOINT ../entrypoint.sh
