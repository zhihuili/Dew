#!/usr/bin/env bash
sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"
. $sbin/conf.sh
$sbin/daemon.sh start com.intel.sto.bigdata.dew.agent.DewDrop $1
