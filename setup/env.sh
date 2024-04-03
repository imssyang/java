#!/bin/bash

# JENV
export JENV_ROOT=/opt/java/jenv
if [[ -f "${JENV_ROOT:-"$HOMEBREW_PREFIX"}/bin/jenv" ]]; then
  eval "$(jenv init - zsh)"
fi

#export JAVA_HOME=$(/usr/libexec/java_home -v 17)
#export JAVA_OPTS="-Xms512m"
#export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 -Xmx512m -Xms512m"
#export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
#export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH
#export MANPATH=$JAVA_HOME/man:$MANPATH
#export CLASSPATH=$JAVA_HOME/sample/CJNIJava
