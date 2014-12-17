cur="`dirname "$0"`"
cur="`cd "$cur"; pwd`"
export DEW_HOME=${cur}/..
classpath=${cur}/sparkpowermeter.jar
java -cp $classpath com.intel.sto.bigdata.app.sparkpowermeter.ui.OfflineAnalysis ${cur}/conf.properties ${cur}/runner.des $@
