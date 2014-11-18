#!/bin/bash
for file in target/app.webcenter-web/WEB-INF/lib/*.jar
do
classpath="$classpath":"`pwd`"/"$file"
done
pwdconf=`pwd`
java -cp $classpath com.intel.sto.bigdata.app.webcenter.logic.ui.JettyServerStart `pwd`/target/app.webcenter-web/
