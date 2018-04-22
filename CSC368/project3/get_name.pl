#!/usr/bin/perl
#------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Project 3
# April 22, 2018
#------------------------------

# Check if exactly one argument was given.
if (@ARGV != 1){
    die "Please enter one argument only.\n";
}

# Check if passwd.bak file exists and accessible.
if (! open PASSWD, "/etc/passwd.bak"){
    die "Could not open passwd.bak: $!";
}

# $line holds line in file with user information.  If remains undefined, then user was not found.
my $line = undef;
while(<PASSWD>){
    if (/$ARGV[0]:/){
        $line = $_;
        my @fields = split ':', $line;
        printf "\nUser ID:%12s\n", $fields[2];
        printf "Home Directory: %s\n\n", $fields[5];
        last; #we found the user, end the loop.
    }
}

# Check if $line was written to.  If not, user does not exist in passwd.bak.
if (! defined $line){
    print "User: $ARGV[0] does not exist.\n";
}

close PASSWD;
