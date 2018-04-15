#!/usr/bin/perl
#------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Project 2
# April 15, 2018
#------------------------------
$num = int(rand(50))+1; # random number selected between 1 and 50
$guess = 0;             # user's provided guess, takes ints and strings
$numberOfGuesses = 0;   # keeps track of guesses provided, both proper and improper guesses.

# keep asking for numbers until guess is equal to the randomly selected number.
while ($guess ne $num){
  # start
  if ($guess eq "0"){
    print "I'm thinking of a number between 1 and 50, what is it? : ";
    $numberOfGuesses+=1;
  }

  # is it between 1 and the number?
  elsif ($guess >= 1 && $guess < $num){
    print "higher: ";
    $numberOfGuesses+=1;
  }

  # is it between the number and 50?
  elsif ($guess > $num && $guess <= 50){
    print "lower: ";
    $numberOfGuesses+=1;
  }

  # did user give quit or exit command?
  elsif ($guess eq "quit" || $guess eq "exit"){
    $numberOfGuesses = 0;
    last;               # break out of while loop.
  }

  # improper input detected, inform user proper usage.
  else{
    print "\nUsage: enter number between 1 and 50\n Type 'exit' to quit.\n Type 'quit' to quit.\n\nTry again: ";
    $numberOfGuesses+=1;
  }
  $guess = <STDIN>;
  chomp($guess);
}

if ($numberOfGuesses > 0){ #player won the game.
  print "Congratulations!\nYou got it in $numberOfGuesses guesses.\n";
} else{ #exit or quit command was issued.
  print "Goodbye.\n";
}
