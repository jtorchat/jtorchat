#!/bin/sh
#
# GNU/Linux does not really require something like RelativeLink.c
# However, we do want to have the same look and feel with similar features.
#
# To run in debug mode simply pass --debug
#
# Copyright 2011 The Tor Project.  See LICENSE for licensing information.
# copy from Tor linux bundle
pidfile="$2.tor.$1.pid"
pid2kill=`cat $pidfile`
kill -9 $pid2kill
rm $pidfile
exit
