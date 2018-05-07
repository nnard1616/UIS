<!--------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Final Project
# May 6, 2018
--------------------------------->
<?php
    require('connect_database.php');
    
    //NOTE: The following will only execute after filling out edit_form.php
    //edit_form.php redirects to this page when successfully filled out.
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        
        $name       = htmlentities($_POST['name']);
        $department = htmlentities($_POST['department']);
        $size       = htmlentities($_POST['size']);
        $price      = htmlentities($_POST['price']);
        $upc        = htmlentities($_POST['upc']);
        $amount     = htmlentities($_POST['amount']);
        
        //delete old row
        $deletion = "DELETE FROM inventory WHERE upc=$upc";
        $connection->query($deletion);
        
        //command to make new row.
        $addition = "INSERT INTO inventory (item_name,department,size,price,upc,amount) "
                              . "VALUES ('$name','$department','$size','$price','$upc','$amount')";
        
        
    }
    
    //only execute when addition query was made.
    if (isset($addition)){
        if (mysqli_query($connection,$addition)){
            echo "Item successfully editted in inventory.";
        } else{
            echo "Error editting item in inventory: " . mysqli_error($connection);
        }
    } else{
        //if no addition query was made, then user gave an unknown upc value.
        echo "Item with given UPC does not exist.";
    }
    
    //close this connection
    mysqli_close($connection);
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Grocery Store Inventory</title>
    </head>
    <h1>Edit Item</h1>
    <body>
        <h2>Edit item in inventory.</h2>
        <!-- Redirect to edit_form.php after successful lookup of upc item -->
        <form method="post" action="edit_form.php">
            <div>
                <label>UPC</label><br>
                <input type="text" name="upc" required>
            </div>
            <button type="submit">Submit</button>
        </form>

    </body>
    <?php require('nav.php');?>
</html>
