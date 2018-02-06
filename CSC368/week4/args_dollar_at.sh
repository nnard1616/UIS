#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 3
# February 11, 2018
#------------------------------
for arg_index in $(seq $#)
do
    #use bash indirection to get actual argument value.
    echo $arg_index: ${!arg_index}
done
