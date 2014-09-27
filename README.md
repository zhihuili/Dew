#Dew#
A lightweight distributed computation framework

##Quick Start##
- Build
-- mvn install

- Start master
-- sbin/master.sh 2052

- Start agent
-- sbin/agent.sh 127.0.0.1:2052

- Run system metrics sample
-- service.sysmetrics/bin/runSample.sh 127.0.0.1:2052
