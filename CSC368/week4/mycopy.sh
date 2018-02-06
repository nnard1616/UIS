#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 3
# February 11, 2018
#------------------------------ 

# mycopy.sh
# Silly middle man-like wrapper for "cp" command line tool

display_help(){
   echo 
   echo "$0  --> Silly middle man-like wrapper for cp command line tool"
   echo
   echo "Usage: $0 [option...] [File to Copy] [Destination]"
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

# Check if 2 or less arguments were given
if [ "$#" -gt 2 ] ; then
    echo Error: too many arguments
    display_help
fi

# Provide cp support for 0, 1, or 2 arguments given.
case $#
in
    0) echo Source file name?
       read file
       echo Destination file name? 
       read dest
       cp $file $dest;;

    1) echo Destination file name? 
       read dest
       cp $1 $dest;;
    2) cp $1 $2;;
esac

