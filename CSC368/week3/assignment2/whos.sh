#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 2
# February 4, 2018
#------------------------------
who | cut --delimiter=' ' -f1 | sort
# who                     -> lists user information
# cut --delimiter=' ' -f1 -> extracts just the usernames
# sort                    -> sorts usernames alphabetically 
