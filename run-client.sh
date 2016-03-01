#!/bin/bash

PORT=4321
HOST=localhost
VER=0.4

java -jar ./bin/server-$VER.jar $HOST $PORT
