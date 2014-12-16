#!/bin/bash
cur="`dirname "$0"`"
cur="`cd "$cur"; pwd`"
export DEW_HOME=${cur}/..
lib=${cur}/target/app.webcenter-web/WEB-INF/lib

for file in ${lib}/*.jar
do
classpath="$classpath":"$file"
done
java -cp $classpath com.intel.sto.bigdata.app.webcenter.logic.ui.JettyServerStart ${cur}/target/app.webcenter-web/
