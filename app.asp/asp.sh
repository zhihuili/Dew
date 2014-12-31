cur="`dirname "$0"`"
cur="`cd "$cur"; pwd`"
export DEW_HOME=${cur}/..
classpath="$cur/asp.jar"
pid=$cur/asp.pid
if [ -f $pid ]; then
  if kill -0 `cat $pid` > /dev/null 2>&1; then
     echo "ERROR: Asp is running now, stop it first." 
     exit 1
  fi
fi
nohup java -cp $classpath com.intel.sto.bigdata.app.asp.ui.Asp $@ > asp.log 2>&1 < /dev/null &
echo $! > $pid
