#!/bin/bash

export JAVA_DISABLE_ENV=yes
export JAVA_HOME=/opt/java
#export JAVA_OPTS="-Xms512m"
#export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 -Xmx512m -Xms512m"
#export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH
export MANPATH=$JAVA_HOME/man:$MANPATH


