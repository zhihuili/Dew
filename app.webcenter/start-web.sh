#!/bin/bash
cur="`dirname "$0"`"
cur="`cd "$cur"; pwd`"
export DEW_HOME=${cur}/..
lib=${cur}/target/app.webcenter-web/WEB-INF/lib

for file in ${lib}/*.jar
do
classpath="$classpath":"$file"
done
nohup java -cp $classpath com.intel.sto.bigdata.app.webcenter.logic.ui.WebCenter ${cur}/target/app.webcenter-web/ > webcenter.log 2>&1 < /dev/null &

