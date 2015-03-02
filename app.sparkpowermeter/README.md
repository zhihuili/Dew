app.sparkpowermeter
==============
A tool which analyze spark application performance base on spark data flow.

Quick Start
---------------
##### Deploy Dew in cluster.
##### Rename conf.properties.template to conf.properties then reset it.
##### Run SparkPowerMeter.

    ./analyze.sh [spark driver log file path]
(How to get spark driver log? eg. ./run.sh > driver.log 2>&1)

OR

    ./analyze.sh startTime(yy/MM/dd HH:mm:ss) endTime(yy/MM/dd HH:mm:ss)
(Just analyze the cluster performance during the time without data flow model.)
For example:
    ./analyze.sh 15/01/01 00:00:00 15/01/01 01:00:00


good luck
