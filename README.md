#Dew#
Big Data Cloud Management Plateform

##Quick Start##

###Start dew cluster###
- Build
-- mvn install -Dhadoop-version=your_deployed_hadoop_version

- Set conf/slaves include your cluster's all nodes
- Set conf/dew.conf

- Start dew cluster
-- sbin/start-all.sh

###Execute dew app###
- [app.sparkpowermeter](/app.sparkpowermeter) execute spark performance analysis
- [app.webcenter](/app.webcenter) start dew web cosole
- [app.asp](/app.asp) apache spark perforamnce
