#!/bin/bash

cmdLine="/data/PragmaticPuppetViewer/corecheck.sh";

number=$(eval $cmdLine);

echo "Number: $number"

case $number in

4)  echo "All OK";
    ;;

*)  echo "PragmagicPuppetViewer not running"
    cd /data/scripts
    ./startppv.sh &
    ;;

esac
