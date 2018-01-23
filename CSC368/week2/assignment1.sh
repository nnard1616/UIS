#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 1
# January 28, 2018
#------------------------------
echo Problem 1
echo 1a.\) "who | grep 'root'" \>\> prints root along with some information about them, such as date/time of login and ip address, if root is logged in.  If root is not logged in, this prints nothing.
echo 1b.\) "ps aux | sort" \>\> prints all of the processes running on the server, sorted in ascending order by characters.
echo 1c.\) "date | cut -c12-16" \>\>  prints the current HH:MM time.
echo
echo Problem 2
echo 2a.\) "who | grep '^[A-Za-z0-9_-]\{4,\}' | cut --delimiter=' ' -f1" \>\>  will print all usernames with 4 or more alphanumeric characters.
who | grep '^[A-Za-z0-9_-]\{4,\}' | cut --delimiter=' ' -f1
echo 2b.\) "cat /etc/passwd | grep '^[A-Za-z0-9_-]\{8,\}' | cut -d: -f1" \>\> will print all users with ids 8 or more characters in length.
cat /etc/passwd | grep '^[A-Za-z0-9_-]\{8,\}' | cut -d: -f1
echo 2c.\) "cat /etc/passwd | grep -c '^[A-Za-z0-9_-]\{8,\}'" \>\> will print the number of users with ids 8 or more characters in length.
cat /etc/passwd | grep -c '^[A-Za-z0-9_-]\{8,\}'
echo 2d.\) "ls -alS ~ | grep '^-' | tr -s ' ' ' ' | cut --delimiter=' ' -f6,10" \>\> will print all files in home directory in decreasing size.
ls -alS ~ | grep '^-' | tr -s ' ' ' ' | cut --delimiter=' ' -f6,10 
echo
echo Problem 3
echo 3a.\) The purpose of grep \(global regular expression print\) is to search for a provided string pattern in a one or more files, or the standard input when no file is provided. It will print every full line that contains the query string pattern.
echo 3b.\) Grep cannot be used to alter files alone, its purpose is simply to read through files.  It has no functionality to alter files.
echo 3c.\) "who | grep 'root'" \>\> will print information about root if they are logged in, nothing otherwise.
echo
echo Problem 4
echo 4a.\) "grep 'UNIX' intro" \>\> will print all lines that contain the string "'Unix'".
grep 'UNIX' intro
echo 4b.\) "grep '[0-9]\+' intro" \>\> will print all lines that contain a number.
grep '[0-9]\+' intro
