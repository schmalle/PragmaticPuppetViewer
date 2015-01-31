#!/bin/sh

#
# simple start script to use a random appliacation secret
#

key="$(date)"
mySecret=`echo -n "value" | openssl sha1 -hmac "$key"`
activator start -Dapplication.secret="$mySecret"