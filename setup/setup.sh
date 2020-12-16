#!/bin/bash

APP=java
HOME=/opt/$APP
SYSD=/etc/systemd/system
SERFILE=jstatd.service

initialize() {
  if [[ ! -s $SYSD/$SERFILE ]]; then
    ln -s $HOME/setup/$SERFILE $SYSD/$SERFILE
    systemctl enable $SERFILE
    echo "($APP) create symlink: $SYSD/$SERFILE --> $HOME/setup/$SERFILE"
  fi

  systemctl daemon-reload
}

deinitialize() {
  if [[ -s $SYSD/$SERFILE ]]; then
    systemctl disable $SERFILE
    rm -rf $SYSD/$SERFILE
    echo "($APP) delete symlink: $SYSD/$SERFILE"
  fi
}

daemon_start() {
  pgrep -x jstatd >/dev/null
  if [[ $? != 0 ]]; then
    systemctl start $SERFILE
    echo "($APP) $SERFILE start!"
  fi

  daemon_show
}

daemon_stop() {
  pgrep -x jstatd >/dev/null
  if [[ $? == 0 ]]; then
    systemctl stop $SERFILE
    echo "($APP) $SERFILE stop!"
  fi

  daemon_show
}

daemon_show() {
  ps -ef | grep jstatd | grep -v 'grep'
}

case "$1" in
  init)
    initialize
    ;;
  deinit)
    deinitialize
    ;;
  start)
    daemon_start
    ;;
  stop)
    daemon_stop
    ;;
  show)
	daemon_show
	;;
  *)
    SCRIPTNAME="${0##*/}"
    echo "Usage: $SCRIPTNAME {init|deinit|start|stop|show}"
    exit 3
    ;;
esac

exit 0

# vim: syntax=sh ts=4 sw=4 sts=4 sr noet
