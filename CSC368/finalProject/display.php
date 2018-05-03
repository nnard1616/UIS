<!--
Written by: Kevin Zepp
Example PHP code provided for CSC 368 Week 14 lecture notes & final project
January 2018
-->
<?php
  require('conn.php');
  
  
  //To Query and Execute Statement
  $sql = "SELECT * FROM students WHERE netid='lullman'";
  $result = mysqli_query($conn,$sql);
  if (!$result) {
    echo 'Could not run query: ' . mysqli_error($conn);
    exit;
  }

  //Start of Table and Column Headings
  print "<table border='1'>\n";
  print " <tr><th>Last Name</th><th>First Name</th><th>NetID</th></tr>\n";

  //Loop to Display contents
  while($row = mysqli_fetch_row($result)) {
  print " <tr>\n";
  print "<td>".$row['0']."</td>";
  print "<td>".$row['1']."</td>";
  print "<td>".$row['2']."</td>";
  print " </tr>\n";
}
print "</table>\n";

// Close Connection
 mysqli_close($conn); 
?>

