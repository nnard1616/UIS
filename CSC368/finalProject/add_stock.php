<!--------------------------------
# Nathan Nard
# nnard2
# CSC 368 - Final Project
# May 6, 2018
--------------------------------->
<?php
    require('connect_database.php');
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $name       = htmlentities($_POST['name']);
        $department = htmlentities($_POST['department']);
        $size       = htmlentities($_POST['size']);
        $price      = htmlentities($_POST['price']);
        $upc        = htmlentities($_POST['upc']);
        $amount     = htmlentities($_POST['amount']);
        
        //command to add item to inventory database
        $addition = "INSERT INTO inventory (item_name,department,size,price,upc,amount) "
                              . "VALUES ('$name','$department','$size','$price','$upc','$amount')";
    }
    
    //only run if addition query was made.
    if (isset($addition)){
        if (mysqli_query($connection,$addition)){
            echo "Item successfully added to inventory.";
        } else{
            echo "Error adding item to inventory: " . mysqli_error($connection);
        }
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
    <h1>Add Item</h1>
    <body>
        <h2>Add item to inventory.</h2>
        <form method="post" action="add_stock.php">
            <div>
                <label>Item Name</label><br>
                <input type="text" name="name" required>
            </div>
            <div>
                <label>Department</label><br>
                <input type="text" name="department" required>
            </div>
            <div>
                <label>Size</label><br>
                <input type="text" name="size" required>
            </div>
            <div>
                <label>Price ($)</label><br>
                <input type="text" name="price" required>
            </div>
            <div>
                <label>UPC</label><br>
                <input type="text" name="upc" required>
            </div>
            <div>
                <label>Amount</label><br>
                <input type="text" name="amount" required>
            </div>
            <button type="submit">Submit</button>
        </form>

    </body>
    <?php require('nav.php');?>
</html>
