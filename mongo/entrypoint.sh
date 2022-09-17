#!/bin/bash

check=$(mongosh /docker-entrypoint-initdb.d/scripts/checkInit.js);
if [ ${check: -1} == "0" ]; then
    
    /docker-entrypoint-initdb.d/scripts/init.sh
    echo "NON C'È NIENTE\n\n\n\n\n\n\n\n\n\n";

else

    echo "C'È QUALCOSA\n\n\n\n\n\n\n\n\n\n"

fi