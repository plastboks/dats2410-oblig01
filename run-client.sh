#!/bin/bash

PORT=4321
HOST=localhost
VER=0.3

java -jar ./bin/server-$VER.jar $HOST $PORT
