#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 3
# February 11, 2018
#------------------------------


# t.sh
# Prints current time in am/pm HH:MM format

display_help(){
   echo 
   echo "$0  --> Prints current time in am/pm HH:MM format"
   echo
   echo "Usage: $0 [option...] "
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

# Check if no argument is given
if [[ $# != 0 ]] ; then
   echo "Error: Too many arguments"
   display_help
   exit 1
fi

hour=$(date | cut -c12-16 | cut --delimiter=":" -f1)
minute=$(date | cut -c12-16 | cut --delimiter=":" -f2)

#determine if time is before or after midday
if   [ "$hour" -eq 0  ] ; then
    meridiem="am"
    hour=12
elif [ "$hour" -lt 12 ] ; then
    meridiem="am"
elif [ "$hour" -eq 12 ] ; then
    meridiem="pm"
elif [ "$hour" -gt 12 ] ; then
    meridiem="pm"
    hour=$(expr $hour - 12)
fi

printf "$hour:$minute $meridiem\n"
