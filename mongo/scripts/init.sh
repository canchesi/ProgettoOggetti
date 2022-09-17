#!/bin/bash

mongoimport --db gallettabot --collection menus --username root --password root --authenticationDatabase admin --type json --file /docker-entrypoint-initdb.d/json/functionalities.json
mongoimport --db gallettabot --collection menus --username root --password root --authenticationDatabase admin --type json --file /docker-entrypoint-initdb.d/json/subjects.json
mongoimport --db gallettabot --collection menus --username root --password root --authenticationDatabase admin --type json --file /docker-entrypoint-initdb.d/json/faq.json