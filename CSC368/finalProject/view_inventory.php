<!--------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Final Project
# May 6, 2018
--------------------------------->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Grocery Store Inventory</title>
    </head>
    <h1>Inventory</h1>
    <body>
        <h2>Here is the whole inventory.</h2>
        <?php
            require('connect_database.php');
            
            $query = mysqli_query($connection, "SELECT * FROM inventory");
            
            //copied from kevin zepp
            if (!$query) {
                echo 'Could not run query: ' . mysqli_error($conn);
                exit;
            }
            
            //Start of Table and Column Headings
            print "<table border='1'>\n";
            print " <tr><th>Item Name</th>"
                . "     <th>Department</th>"
                . "     <th>Size</th>"
                . "     <th>Price</th>"
                . "     <th>UPC</th>"
                . "     <th>Amount</th></tr>\n";

            //Loop to Display contents
            while($row = mysqli_fetch_row($query)) {
                print " <tr>\n";
                print "<td>".$row['0']."</td>";
                print "<td>".$row['1']."</td>";
                print "<td>".$row['2']."</td>";
                print "<td>".$row['3']."</td>";
                print "<td>".$row['4']."</td>";
                print "<td>".$row['5']."</td>";
                print " </tr>\n";
            }
            print "</table>\n";
            // Close Connection
            mysqli_close($connection); 
            //end copy
            require('nav.php');
            ?>
    </body>
</html>