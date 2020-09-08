#!/bin/sh

BASEDIR=`dirname $0`/..
BASEDIR=`(cd "$BASEDIR"; pwd)`
echo current path:$BASEDIR

BASEBIN_DIR=$BASEDIR"/bin"
cd $BASEBIN_DIR

SAF_PIDPATH="$BASEBIN_DIR"

GC_DATE=`date +%Y-%m-%d-%H-%M`

LOG_PATH="/data/http-mock-server/logs"

JVM_FILE="-XX:+UseCondCardMark -XX:+UseConcMarkSweepGC -XX:CMSWaitDuration=250"
JVM_FILE="$JVM_FILE -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:${LOG_PATH}/gc-${GC_DATE}.log -XX:ErrorFile=${LOG_PATH}/hs_err_pid%p-${GC_DATE}.log"
JVM_FILE="$JVM_FILE -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=10M"
JVM_FILE="$JVM_FILE -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_PATH}/java_pid%p.hprof"

if [ "$1" != "" ]; then
    SAF_PIDPATH="$1"
fi

# ------ check if server is already running
PIDFILE=$SAF_PIDPATH"/startup.pid"
if [ -f $PIDFILE ]; then
    if kill -0 `cat $PIDFILE` > /dev/null 2>&1; then
        echo server already running as process `cat $PIDFILE`.
        exit 0
    fi
fi

JAVACMD=""
# ------ set JAVACMD
# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute $JAVACMD"
  exit 1
fi

# ------ set jvm memory
if [ -z "$OPTS_MEMORY" ] ; then
    OPTS_MEMORY="-server -Xms2048m -Xmx2048m -XX:MaxDirectMemorySize=96M -Dio.netty.allocator.type=pooled -Dio.netty.allocator.tinyCacheSize=0 -Dio.netty.allocator.smallCacheSize=0 -Dio.netty.allocator.normalCacheSize=0"
fi

# ------ set CLASSPATH
CLASSPATH="$BASEDIR"/conf/:"$BASEDIR"/lib/*
MAINCLASS=cn.shaikuba.mock.MockServerApplication
echo "$CLASSPATH"

# DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5006"

# ------ run proxy
nohup "$JAVACMD" $JAVA_OPTS \
  $OPTS_MEMORY $JVM_FILE \
  -classpath "$CLASSPATH" \
  -Dbasedir="$BASEDIR" \
  -Dfile.encoding="UTF-8" \
  $MAINCLASS \
  > /dev/null &


# ------ wirte pid to file
if [ $? -eq 0 ]
then
    if /bin/echo -n $! > "$PIDFILE"
    then
        sleep 1
        echo STARTED SUCCESS
    else
        echo FAILED TO WRITE PID
        exit 1
    fi
#    tail -100f $LOGFILE
else
    echo SERVER DID NOT START
    exit 1
fi
