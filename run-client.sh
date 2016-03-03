#!/bin/bash

PORT=4321
HOST=localhost
VER=final

java -jar ./bin/server-$VER.jar $HOST $PORT
