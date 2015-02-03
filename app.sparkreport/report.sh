cur="`dirname "$0"`"
cur="`cd "$cur"; pwd`"
export DEW_HOME=${cur}/..
classpath="$cur/report.jar"
java -cp $classpath com.intel.sto.bigdata.app.sparkreport.ui $@ 
