#!/bin/bash
JAVA_VER=$(java -version 2>&1 | sed 's/java version "\(.*\)\.\(.*\)\..*"/\1\2/; 1q')
if [ "$JAVA_VER" -ge 16 ]; then
java -jar jtorchat.jar
else
xterm -e editor getJava.txt
fi
#This file start the Info Text file when the Java Version not exist or is not new enough
#when java is installed and new enough it start jtorchat with the path from pwd