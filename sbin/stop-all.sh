#!/usr/bin/env bash

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"

$sbin/stop-agents.sh
$sbin/stop-master.sh

