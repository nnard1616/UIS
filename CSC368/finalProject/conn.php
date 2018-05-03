<!--
Written by: Kevin Zepp
Example PHP code provided for CSC 368 Week 14 lecture notes & final project
January 2018
-->
<?php
  //enable PHP error reporting
  ini_set ("display_errors", "1");
  error_reporting(E_ALL);

  //Setup some variables
  $host='127.0.0.1';  //mysql server is on localhost
  $user='netid';   //your NetID
  $pass='password';   //don't use your NetID password!! Change it as described in Final Project
  $db='database';    //database name is the same as your NetID
  
  //Login to MySQL Server
  $conn = mysqli_connect($host,$user,$pass,$db) or die(mysqli_connect_error());

  //for this example assume a table named 'students' already exists
?>

