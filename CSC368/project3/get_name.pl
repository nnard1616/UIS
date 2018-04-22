#!/usr/bin/perl
#------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Project 3
# April 22, 2018
#------------------------------
use strict;

if (! open PASSWD, "/etc/passwd.bak"){
  die "Could not open passwd.bak: $!";
}

close PASSWD;
