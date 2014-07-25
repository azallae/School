<?php
require 'ConnectSQLdb.php'; // make sure connectDB exist to continue

if(isset($_POST['acc_name'])){ //if it's set a variable has been passed
	$acc_name = mysql_real_escape_string($_POST['acc_name']); // for security, to prevent injection
	if(!empty($acc_name)){
		$acct_name_command = mysql_query("SELECT COUNT(`projName`) FROM `dataman_database` WHERE `projName`='$acc_name'"); // search for element in the column projName
		$name_count = mysql_result($acct_name_command, 0); //returns the count 
		if($name_count == 0){ // returns message if account name was not found
			echo '0';
		}
		else if($name_count == 1){ // returns message if account name was found
			echo '1';

		}
	}
}
mysql_close();
?>