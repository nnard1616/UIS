#!/usr/bin/perl
#------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Project 2
# April 14, 2018
#------------------------------
$num = int(rand(50))+1;
$guess = 0;
$numberOfGuesses = 0;

while ($guess ne $num){
  if ($guess eq "0"){
    print "I'm thinking of a number between 1 and 50, what is it? : ";
    $numberOfGuesses+=1;
  } elsif ($guess >= 1 && $guess < $num){
    print "higher: ";
    $numberOfGuesses+=1;
  } elsif ($guess > $num && $guess <= 50){
    print "lower: ";
    $numberOfGuesses+=1;
  } elsif ($guess eq "quit" || $guess eq "exit"){
    $numberOfGuesses = 0;
    last;
  } else{
    print "\nUsage: enter number between 1 and 50\n Type 'exit' to quit.\n Type 'quit' to quit.\n\nTry again: ";
  }
  $guess = <STDIN>;
  chomp($guess);
}

if ($numberOfGuesses > 0){ #player won the game.
  print "Congratulations!\nYou got it in $numberOfGuesses guesses.\n";
} else{ #exit or quit command was issued.
  print "Goodbye.\n";
}
