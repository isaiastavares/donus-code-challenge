#!/bin/bash

java ${JAVA_OPTS} \
 "-Dspring.profiles.active=docker" \
 -jar "donus-code-challenge.jar"