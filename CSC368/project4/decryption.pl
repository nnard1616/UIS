#!/usr/bin/perl
#------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Project 4
# April 29, 2018
#------------------------------

# Check if exactly one argument was given.
if (@ARGV != 1){
    die "Please enter one argument only, a filename to be decrypted.\n";
}

my $filenameextension = substr $ARGV[0], -4;

# Check if file is a .enc file.
if ( $filenameextension ne ".enc"){
  die "Please provide a '.enc' file to be decrypted.\n";
}

# Check if given file name exists and accessible, then open it.
open my $fh, '<', $ARGV[0] or die "error opening $ARGV[0]: $!";
my $data = do { local $/; <$fh> };
close $fh;

# transliterate.
$data =~ tr/zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA/a-zA-Z/;

# write
open(my $fh, '>', 'OriginalFile.dec');
print $fh $data;
close $fh;

print "done\n";
