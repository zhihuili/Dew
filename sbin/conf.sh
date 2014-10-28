#!/usr/bin/env bash

if [ "$DEW_MASTER_PORT" = "" ]; then
  export DEW_MASTER_PORT=6066
fi

if [ "$DEW_MASTER_NAME" = "" ]; then
  export DEW_MASTER_NAME=$HOSTNAME
fi

this="${BASH_SOURCE:-$0}"
# convert relative path to absolute path
config_bin="`dirname "$this"`"
script="`basename "$this"`"
config_bin="`cd "$config_bin"; pwd`"
this="$config_bin/$script"

export DEW_HOME="`dirname "$this"`"/..
export DEW_CONF=$DEW_HOME/conf
