#!/bin/bash

sed -i "s/TOKEN/$TELEGRAM_TOKEN/" ./classes/config.xml
sed -i "s/URL/$MONGO_URL/"   ./classes/config.xml
sed -i "s/USER/$MONGO_USER/" ./classes/config.xml
sed -i "s/PASS/$MONGO_PASS/" ./classes/config.xml

java -jar GallettaBot.jar
