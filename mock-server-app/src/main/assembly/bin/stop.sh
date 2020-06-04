#!/bin/sh

BASEDIR=`dirname $0`
BASEDIR=`(cd "$BASEDIR"; pwd)`
echo current path $BASEDIR

SAF_PIDPATH="$BASEDIR"

if [ "$1" != "" ]; then
    SAF_PIDPATH="$1"
fi

PIDFILE=$SAF_PIDPATH"/startup.pid"
echo $PIDFILE

if [ ! -f "$PIDFILE" ]
then
    echo "no process to stop (could not find file $PIDFILE)"
else
    kill -9 $(cat "$PIDFILE")
    rm -f "$PIDFILE"
    echo STOPPED
fi
exit 0

echo stop finished.