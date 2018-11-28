#!/bin/bash
export LANG="en_US.UTF-8"
export JAVA_HOME=/usr/local/java/jdk/latest
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

java -jar /usr/local/tcboot/nginx-monitoring/monitoring-ip.jar >> /usr/local/tcboot/nginx-monitoring/logs/catalina_$(date "+%Y-%m-%d").out  2>&1 &
# echo "over!"
# set ff=unix    !!!!!!!!!!!!!!notice!!!!!!!!!!!!!!