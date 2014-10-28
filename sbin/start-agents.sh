#!/usr/bin/env bash

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"
. $sbin/conf.sh
exec "$sbin/agents.sh" cd "$DEW_HOME" \; "$sbin/start-agent.sh" "$DEW_MASTER_NAME:$DEW_MASTER_PORT"
