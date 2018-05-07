<!--------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Final Project
# May 6, 2018
--------------------------------->
<?php
    require('connect_database.php');
    
    //handle information from edit_start.php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        
        //grab upc from user
        $upc = htmlentities($_POST['upc']);
        
        //query to get row information
        $query = mysqli_query($connection, "SELECT * FROM inventory WHERE upc='$upc'");
        
        //extract row array of information
        
        $row = mysqli_fetch_row($query);
        
        $name       = $row['0'];
        $department = $row['1'];
        $size       = $row['2'];
        $price      = $row['3'];
        $upc2       = $row['4'];
        $amount     = $row['5'];
        
        if (!isset($upc2)){
            header("Location: edit_start.php"); /* Redirect browser */
            exit();
        }
    }
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Grocery Store Inventory</title>
    </head>
    <h1>Edit Item</h1>
    <body>
        <?php print "<h2>Now editing item with UPC of $upc</h2>" ?>
        <!-- redirects back to edit_start.php after successful completion of form -->
        <form method="post" action="edit_start.php">
            <div>
                <label>Item Name</label><br>
                <?php print "<input type=\"text\" name=\"name\" value=\"$name\" required>"; ?>
            </div>
            <div>
                <label>Department</label><br>
                <?php print "<input type=\"text\" name=\"department\" value=\"$department\" required>"; ?>
            </div>
            <div>
                <label>Size</label><br>
                <?php print "<input type=\"text\" name=\"size\" value=\"$size\" required>"; ?>
            </div>
            <div>
                <label>Price ($)</label><br>
                <?php print "<input type=\"text\" name=\"price\" value=\"$price\" required>"; ?>
            </div>
            <div>
                <label>UPC</label><br>
                <?php print "<input type=\"text\" name=\"upc\" value=\"$upc\" required>"; ?>
            </div>
            <div>
                <label>Amount</label><br>
                <?php print "<input type=\"text\" name=\"amount\" value=\"$amount\" required>"; ?>
            </div>
            <button type="submit">Submit</button>
        </form>
        <?php require('nav.php');?>
    </body>
</html>