#!/usr/bin/bash
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 2
# February 4, 2018
#------------------------------
ls -al $PWD | grep '^-' | wc -l
# ls -al $PWD  -> lists ALL (hidden included) files/folders within the script caller's current working directory.
# grep '^-'    -> filters out the folders, keeps just the files.
# wc -l        -> counts how many lines are retained by grep, which is the number of files, including hidden files and excluding folders.
