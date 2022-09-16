#!/bin/bash

sed -i "s/TOKEN/$TELEGRAM_TOKEN/" ./src/main/resources/config.xml
sed -i "s/URL/$MONGO_URL/"   ./src/main/resources/config.xml
sed -i "s/USER/$MONGO_USER/" ./src/main/resources/config.xml
sed -i "s/PASS/$MONGO_PASS/" ./src/main/resources/config.xml
sed -i "s/EXTRA/$MONGO_EXTRA/" ./src/main/resources/config.xml
sed -i "s/PREFIX/$MONGO_PREFIX/" ./src/main/resources/config.xml

java -jar target/GallettaBot.jar
