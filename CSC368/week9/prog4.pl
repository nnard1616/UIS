#!/usr/bin/perl
#------------------------------
# Nathan Nard 
# nnard2
# CSC 368 - Assignment 9
# March 24, 2018
#------------------------------

$r = -1;
while($r < 0){
    print "Please provide a radius that is >= 0: ";
    chomp($r = <STDIN>);
}

print "Circumferencec is: " . 2 * $r * 3.14 . "\n";
