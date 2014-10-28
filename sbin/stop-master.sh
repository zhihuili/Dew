#!/usr/bin/env bash
sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"
$sbin/daemon.sh stop com.intel.sto.bigdata.dew.master.Herse

