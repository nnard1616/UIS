<!--
Written by: Kevin Zepp
Example PHP code provided for CSC 368 Week 14 lecture notes & final project
January 2018
-->
<?php
  require('conn.php');
  
  //Insert Statement
  $insert = "INSERT INTO students (first_name,last_name,netid) VALUES ('Larry','Ullman','lullman')";
  if (mysqli_query($conn,$insert)) {
    echo "Values inserted!\n";
  }
  else {
    echo "Error inserting values: " . mysqli_error($conn);
  }
  
  // Close Connection
  mysqli_close($conn); 
?>

