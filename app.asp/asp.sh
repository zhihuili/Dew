cur="`dirname "$0"`"
cur="`cd "$cur"; pwd`"
export DEW_HOME=${cur}/..
classpath="$cur/asp.jar"

nohup java -cp $classpath com.intel.sto.bigdata.app.asp.ui.Asp $@ > asp.log 2>&1 < /dev/null &
