<!--
Written by: Kevin Zepp
Example PHP code provided for CSC 368 Week 14 lecture notes & final project
January 2018
-->
<?php
  require('conn.php');

  echo "This page is currently non-functional";
    
  //To Update a record:
  //mysqli_query($conn,"UPDATE students SET last_name='Ullman', first_name ='Lenny' WHERE netid='lullman'");

  //To Delete a record:
  //mysqli_query($conn,"DELETE FROM students WHERE netid='lullman'");

// Close Connection
 mysqli_close($conn); 
?>
