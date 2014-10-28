#!/usr/bin/env bash

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"

exec "$sbin/agents.sh" cd "$DEW_HOME" \; "$sbin/stop-agent.sh"
