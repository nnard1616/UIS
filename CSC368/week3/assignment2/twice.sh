#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 2
# February 4, 2018
#------------------------------

# Check if only one argument was given.
if [[ $# != 1 ]] ; then
   echo "Error: Too many arguments";
   echo "Usage: twice.sh <integer>";
   exit 2
fi

# Check if argument is a integer. 
re='^[0-9]+$'
if ! [[ $1 =~ $re ]] ; then
   echo "Error: Not an integer";
   echo "Usage: twice.sh <integer>";
   exit 1
fi
expr $1 \* 2
