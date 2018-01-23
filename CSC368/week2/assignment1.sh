#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 1
# January 28, 2018
#------------------------------
echo Problem 1
echo 1a.\) "who | grep 'root'" \>\> does not print anything
echo 1b.\) "ps aux | sort" \>\> prints all of the processes running on the server, sorted.
echo 1c.\) "date | cut -c12-16" \>\>  prints the current HH:MM time.
echo
echo Problem 2
echo 2a.\) "who | grep '^\w\{4,\}' | cut --delimiter=' ' -f1" \>\>  will print all usernames with 4 or more alphanumeric characters
who | grep '^\w\{4,\}' | cut --delimiter=' ' -f1
echo 2b.\) "cat /etc/passwd | grep '^[A-Za-z0-9_-]\{8,\}' | cut -d: -f1" \>\> will print all users with ids 8 or more characters in length.
cat /etc/passwd | grep '^[A-Za-z0-9_-]\{8,\}' | cut -d: -f1
