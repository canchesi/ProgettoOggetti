#!/bin/bash

check=$(mongosh /docker-entrypoint-initdb.d/scripts/checkInit.js);
if [ ${check: -1} == "0" ]; then
    
    /docker-entrypoint-initdb.d/scripts/init.sh

fi
