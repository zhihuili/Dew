#!/usr/bin/env bash

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"

$sbin/start-master.sh

$sbin/start-agents.sh
