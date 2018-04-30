#!/usr/bin/perl
#------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Project 4
# April 29, 2018
#------------------------------

# Check if exactly one argument was given.
if (@ARGV != 1){
    die "Please enter one argument only, a filename to be encrypted.\n";
}

# Check if given file name exists and accessible, then open it.
open my $fh, '<', $ARGV[0] or die "error opening $ARGV[0]: $!";
my $data = do { local $/; <$fh> };
close $fh;

# transliterate.
$data =~ tr/a-zA-Z/zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA/;

# write
open(my $fh, '>', 'OriginalFile.enc');
print $fh $data;
close $fh;

print "done\n";
