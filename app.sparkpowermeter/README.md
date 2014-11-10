app.sparkpowermeter
==============
A tool which analyze spark application performance base on spark data flow.

Quick Start
---------------
- Deploy Dew in cluster.
- Rename conf.properties.template to conf.properties then reset it.
- Run SparkPowerMeter

    ./analyze.sh [spark driver log file path]
(How to get spark driver log? eg. ./run.sh > driver.log 2>&1)

OR

    ./analyze.sh startTime(yy/MM/dd HH:mm:ss) endTime(yy/MM/dd HH:mm:ss)
(Just analyze the cluster performance during the time without data flow model.)

- Check performance analysis result

Analysis result will be output to $workload.output.path(analyze.sh will print the path in console.)

OR

You can review it by Web browser.
(
TODO
)

good luck
