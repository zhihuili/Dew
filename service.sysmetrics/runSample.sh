cur="`dirname "$0"`"
cur="`cd "$cur"; pwd`"
export DEW_HOME=${cur}/..
classpath="$DEW_HOME/dew.assembly/dew.jar"
java -cp $classpath com.intel.sto.bigdata.dew.service.sysmetrics.cli.ExampleMain $HOSTNAME:6766

