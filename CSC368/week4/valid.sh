#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 3
# February 11, 2018
#------------------------------

# valid.sh
# Determines if given argument string represents a valid shell variable name.

display_help(){
   echo 
   echo "$0  --> Determines if given argument string represents a valid shell variable name."
   echo
   echo "Usage: $0 [option...] <String>"
   echo 
   echo "   -h, 	Display this help dialogue"
}

# Read options, if any 
while getopts h opt
do
   case $opt
      in 
         h) display_help; exit 0;;
         *) display_help; exit 2;;
   esac
done

# Check if only one argument is given
if [[ $# != 1 ]] ; then
   echo "Error: Too many arguments"
   display_help
   exit 1
fi

# Check to see if given argument is a valid shell variable name
valid="^[A-Za-z_]"
if [[ $1 =~ $valid ]] ; then
   echo "yes"
else
   echo "no"
fi

