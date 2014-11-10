#!/bin/bash

rm -f ./*.dat
TARGET=("sr479" "sr480" "sr481" "sr482")

for worker in ${TARGET[@]}
do
ssh $worker 'rm -f ./dstat_'${worker}'.dat &'
ssh $worker dstat --mem --io --cpu --net -N eth0,eth1,total --disk --output dstat_${worker}.dat 2 > /dev/null &
done
./run.sh > nweightlog.log 2>&1
sleep 40
for worker in ${TARGET[@]}
do
ssh $worker ps aux | grep -i "dstat" | grep -v "grep" | awk '{print $2}' | xargs ssh $worker kill -9
done
for worker in ${TARGET[@]}
do
scp ${worker}:./dstat_${worker}.dat /home/frank/liye/workload/youku/nweight/
done
