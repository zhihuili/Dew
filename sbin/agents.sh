#!/usr/bin/env bash

sbin="`dirname "$0"`"
sbin="`cd "$sbin"; pwd`"
. $sbin/conf.sh

DEW_AGENTS=$DEW_CONF/slaves
if [ -f $DEW_AGENTS ]; then
  AGENTS_LIST=`cat $DEW_AGENTS`
fi

if [ "$AGENTS_LIST" = "" ]; then
  AGENTS_LIST=localhost
fi

for slave in `echo "$AGENTS_LIST"|sed  "s/#.*$//;/^$/d"`; do
  ssh "$slave" $"${@// /\\ }" \
    2>&1 | sed "s/^/$slave: /" &
done

wait
