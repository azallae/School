<?php

/*********************************************************
 *					INSERT INTO PROJECT                  *                  
 *********************************************************/

/*****Set variables from posted HTML form data*****/
$account_name=mysql_real_escape_string($_POST['account_name']);
$database_name=mysql_real_escape_string($_POST['account_name']);
$disk_quota=mysql_real_escape_string($_POST['disk_quota']);
$major=mysql_real_escape_string($_POST['major']);
$group=mysql_real_escape_string($_POST['group']);
$project_advisor=mysql_real_escape_string($_POST['project_advisor']);
$advisor_email=mysql_real_escape_string($_POST['advisor_email']);
$shell=mysql_real_escape_string($_POST['shell']);
$num_students=mysql_real_escape_string($_POST['num_students']);
$num_semesters=mysql_real_escape_string($_POST['num_semesters']);
$comments=mysql_real_escape_string($_POST['comments']);
$status=mysql_real_escape_string('Pending');// Includes things in phpseclib0 that is necessary to ssh

/*****Make num_semesters the correct number*****/
if ($num_semesters != 1 || $num_semesters != 2)
{ $num_semesters = mysql_real_escape_string($_POST['num_semesters_other']); }

/*****Removes asterisks for insert*****/
if ($shell == '***Bash***') { $shell = 'Bash'; }
else if ($shell == '***Ksh***') { $shell = 'Ksh'; }
else if ($shell == '***Sh***') { $shell = 'Sh'; }
else if ($shell == '***Tcsh***') { $shell = 'Tcsh'; }

/*****The database requires 'remote' or 'localhost'*****/
$host=mysql_real_escape_string($_POST['mysql_host_location']);
if ($host == '%') {	$host = mysql_real_escape_string('remote'); }

/*****Finds Expiring Dates*****/
$todayDate= date("m");
$yearTwo = date("Y");
if($todayDate >= 1 && $todayDate <= 7){ // Applying during Spring semester
	if($num_semesters == 1){
		$tmp_One = gregoriantojd ( 7, 31, $yearTwo );
		$tmp_One = jdtogregorian($tmp_One);
		$tmp_One = date('Y-m-d', strtotime($tmp_One));
		$expireTwo = mysql_real_escape_string($tmp_One);
	}else{
		for($i = 0; $i < $num_semesters-1; $i++){
			if($i % 2 == 0){
				$tmp_Two = gregoriantojd ( 12, 31, $yearTwo);
				$tmp_Two = jdtogregorian($tmp_Two);
				$tmp_Two = date('Y-m-d', strtotime($tmp_Two));
				$expireTwo = mysql_real_escape_string($tmp_Two);
			}else{
				$yearTwo++;
				$tmp_Three = gregoriantojd ( 7, 31, $yearTwo);
				$tmp_Three = jdtogregorian($tmp_Three);
				$tmp_Three = date('Y-m-d', strtotime($tmp_Three));
				$expireTwo = mysql_real_escape_string($tmp_Three);
			}
		}
	}
}else{ // Applying during Fall semester
	if($num_semesters == 1){
	$tmp_Four = gregoriantojd ( 12, 31, $yearTwo );
	$tmp_Four = jdtogregorian($tmpFour);
	$tmp_Four = date('Y-m-d', strtotime($tmp_Four));
	$expireTwo = mysql_real_escape_string($tmp_Four);
	}else{
		for($i = 0; $i < $num_semesters-1; $i++){
			if($i % 2 == 0){
			    $yearTwo++;
				$tmp_Five = gregoriantojd ( 7, 31, $yearTwo);
				$tmp_Five = jdtogregorian($tmpFive);
				$tmp_Five = date('Y-m-d', strtotime($tmp_Five));
				$expireTwo = mysql_real_escape_string($tmp_Five);
			}else{
				$tmpSix = gregoriantojd ( 12, 31, $yearTwo);
				$tmpSix = jdtogregorian($tmpSix);
				$tmpSix = date('Y-m-d', strtotime($tmpSix));
				$expireTwo = mysql_real_escape_string($tmpSix);
			}
		}
	}
}
/******************************
 *		INSERT INTO PROJECT   *
 ******************************/

/*****Connects to project database on server*****/
$projCon=mysqli_connect("131-server.ecs.csus.edu","root","have.phun","project");
// Check connection
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

/*****Insert main account info into project database*****/
$sql="INSERT INTO project_info (projName, quota, groupName, dateExpires, status, advisor_name, advisor_email, shell)
VALUES
('$account_name', '$disk_quota', '$group', '$expireTwo', 'Pending', '$project_advisor', '$advisor_email', '$shell')";

if (!mysqli_query($projCon,$sql))
{
	die('Error: ' . mysqli_error($projCon));
}

/*****Insert comments to project database*****/
if ($comments != "")
{
	$today = date("Y-m-d");
	$today = mysql_real_escape_string($today);
	
	$sql="INSERT INTO project_history (hisDetail, projName, hisShortDesc, hisDate)
	VALUES
	('$comments', '$account_name', 'Application Comments', '$today')";


	if (!mysqli_query($projCon,$sql))
	{
		die('Error: ' . mysqli_error($projCon));
	}
}

/*****Inserts Student Contact Information*****/
for ($i = 1;  $i <= $num_students; $i++)
{
	if ($num_students > 1)
	{
		$maj = $_POST['major'.$i];
	}
	else
	{
		$maj = $major;
	}
	$lname = $_POST['lname'.$i];
	$fname = $_POST['fname'.$i];
	$phone = $_POST['phone'.$i];
	$email = $_POST['email'.$i];
	
	$sql="INSERT INTO project_people (deptMajor, pNameLast, pNameFirst, phoneNum, email, projName)
	VALUES
	('$maj', '$lname', '$fname', '$phone', '$email', '$account_name')";

	if (!mysqli_query($projCon,$sql))
	{
		die('Error: ' . mysqli_error($projCon));
	}
}

mysqli_close($projCon); //Close connection

/*********************************************************
 *					INSERT INTO DATAMAN                  *
 *********************************************************/

/*****Connect to dataman database*****/
$MySQLcon=mysqli_connect("131-server.ecs.csus.edu","saveInfo","NewAccounts!!","dataman");
// Check connection
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
  
/*****Insert main database info*****/
$sql="INSERT INTO dataman_database (projName, server, dateExpires, status, advisor_name, advisor_email, groupName)
VALUES
('$database_name', '$host', '$expireTwo', '$status', '$project_advisor', '$advisor_email', '$group')";

if (!mysqli_query($MySQLcon,$sql))
{
	die('Error: ' . mysqli_error($MySQLcon));
}

/*****Set individual permissions based on form data*****/
$permissions=mysql_real_escape_string($_POST['permissions_radio']);
$alter=mysql_real_escape_string($_POST['alter']);
$delete=mysql_real_escape_string($_POST['delete']);
$index=mysql_real_escape_string($_POST['index']);
$insert=mysql_real_escape_string($_POST['insert']);
$select=mysql_real_escape_string($_POST['select']);
$update=mysql_real_escape_string($_POST['update']);
$create=mysql_real_escape_string($_POST['create']);
$drop=mysql_real_escape_string($_POST['drop']);
$ref=mysql_real_escape_string($_POST['ref']);

/*****Insert mysql permissions*****/
if ($host == 'localhost') {	$host = mysql_real_escape_string('local'); }
$sql="INSERT INTO dataman_dbaccounts (DBAccountUName, projName, db_host, db_alter, db_index, db_reference, db_drop, db_create, db_delete, db_update, db_insert, db_select)
VALUES
('$database_name', '$database_name', '$host', '$alter', '$index', '$ref', '$drop', '$create', '$delete', '$update', '$insert', '$select')";

if (!mysqli_query($MySQLcon,$sql))
{
	die('Error: ' . mysqli_error($con));
}

/*****Insert comments, if any*****/
if ($comments != "")
{
	$today = date("Y-m-d");
	$today = mysql_real_escape_string($today);
	
	$sql="INSERT INTO dataman_history (hisDetail, projName, hisShortDesc, hisDate)
	VALUES
	('$comments', '$database_name', 'Application Comments', '$today')";


	if (!mysqli_query($MySQLcon,$sql))
	{
		die('Error: ' . mysqli_error($con));
	}
}

/*****Inserts student contact Information*****/
$studentInfo = "";
$horizontal = '________________________________________________________'; // Horizontal line use to divide the section in the email
for ($i = 1;  $i <= $num_students; $i++)
{
	if ($num_students > 1)
	{
		$maj = $_POST['major'.$i];
	}
	else
	{
		$maj = $major;
	}
	
	$lname = $_POST['lname'.$i];
	$fname = $_POST['fname'.$i];
	$phone = $_POST['phone'.$i];
	$email = $_POST['email'.$i];
	$studentInfo .= 'Student #'.$i.' Info:\n\nName: '.$fname.' '.$lname.'\nPhone: '.$phone.'\nEmail: '.$email.'\n'.$horizontal.'\n\n';

	$sql="INSERT INTO dataman_people (deptMajor, pNameLast, pNameFirst, phoneNum, email, projName)
	VALUES
	('$maj', '$lname', '$fname', '$phone', '$email', '$database_name')";

	if (!mysqli_query($MySQLcon,$sql))
	{
		die('Error: ' . mysqli_error($MySQLcon));
	}
}

mysqli_close($MySQLcon); //close connection

/*****SENDS EMAIL TO ADMIN*****/
mysql_connect('131-server.ecs.csus.edu', 'root', 'have.phun'); // Connects to server
mysql_select_db('project'); // Selects a database
$countOne = mysql_query("SELECT COUNT(`status`) FROM `project_info` WHERE `status`='Pending'"); // Counts Pending accounts in the Project database
$statusOne = mysql_result($countOne, 0);
mysql_close();
mysql_connect('131-server.ecs.csus.edu', 'saveInfo', 'NewAccounts!!'); // Connects to server
mysql_select_db('dataman'); // Selects a database
$countTwo = mysql_query("SELECT COUNT(`status`) FROM `dataman_database` WHERE `status`='Pending'"); // Counts Pending accounts in the Dataman database
$statusTwo = mysql_result($countTwo, 0);
set_include_path(get_include_path() . PATH_SEPARATOR . 'php/phpseclib0');
include('Net/SSH2.php');
$ssh = new Net_SSH2('athena.ecs.csus.edu'); //Starting the ssh connection to localhost
if (!$ssh->login('131win9', 'roezyuzf')) { // If you can't log on...
	exit('Login Failed');
}
date_default_timezone_set('America/Los_Angeles');// Sets time to PDT
$timeStamp = date('D M d h:i:s Y'); // Gets the month, date, time, and year.

$ssh->exec('cd ..');
/******************************************************************************
 *This sends the email with all the content, it will not work if it's divided.* 
 *ADMIN EMAIL is added at the end of this line------------------------------->*
 *Current Email Added: lynne@ecs.csus.edu                                     *
 ******************************************************************************/
$ssh->exec('echo "Hello,\n\nYou have received a new application for both MySQL and Project Account.\n\nProject Account Pending Applications: '.$statusOne.'\nMySQL Database Pending Applications: '.$statusTwo.'\n'.$horizontal.'\n\nUser submitted the following data on '.$timeStamp.', for a Project account: \nAccount Name: '.$account_name.'\nDisk Quota: '.$disk_quota.'\nMajor: '.$major.'\nProject Advisor: '.$project_advisor.'\nAdvisor Email: '.$advisor_email.'\nAccount Type: '.$shell.'\nNumber of Students: '.$num_students.'\nNumber of Semesters: '.$num_semesters.'\nComments: '.$comments.'\n'.$horizontal.'\n\nUser submitted the following data on '.$timeStamp.', for a Dataman account: \nMajor: '.$major.'\nProject Advisor: '.$project_advisor.'\nAdvisor Email: '.$advisor_email.'\nNumber of Participants: '.$num_students.'\nNumber of Semester: '.$num_semesters.'\nDatabase Name: '.$database_name.'\nMysql Host Location: '.$host.'\nMysql Permissions: '.$permissions.'\nComments: '.$comments.'\n'.$horizontal.'\n\n'.$studentInfo.'Have a good day!" | mailx -r "noreply" -s "New Project and MySQL Applicant" "lynne@ecs.csus.edu"');
mysql_close();
?>
<!-----The Submit Page----->
<!-----This webpage is similar to the webpage seen after logging out from my.csus.edu----->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- saved from url=(0027)http://www.csus.edu/logout/ -->
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml" lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link charset="UTF-8" href="./Logout_files/reset-min.css" media="all" rel="stylesheet" type="text/css">
    <link charset="utf-8" href="./Logout_files/seafoam2.css" media="all" rel="stylesheet" title="Default Styles" type="text/css">
    <link charset="UTF-8" href="./Logout_files/private.css" media="all" rel="stylesheet" type="text/css">
	<!---------------------------------------->
	<!-----Ridirect User Back To Homepage----->
	<!---------------------------------------->
	<script type="text/javascript">
		var time_left = 11;
		var cinterval;
		var url = "http://www.ecs.csus.edu";
		function countdown(){
			time_left--;
			document.getElementById("box").innerHTML = time_left; // Countdown
			if(time_left == 0){
				clearInterval(cinterval);
				window.location = url;
			}			
		}
		cinterval = setInterval('countdown()', 1000);
	</script>
	<!---------------------------------------->
	<!---------------------------------------->
	<!---------------------------------------->
    <title>
		Sacramento State - Redirect
    </title>
	</head>
	<body onload="countdown();">
		<p>
			<a class="hide_focus" href="http://www.csus.edu/logout/#content">Skip to Content</a>
		</p>
		<div class="topshadow"></div>
		<div class="shadowcontainer">
			<div class="maincontainer container_16">
				<div class="header">
					<img src="./Logout_files/emptyword.jpg"/>
				</div>
				<div class="double_dotted_line"></div>
					<div class="page_body">
						<div class="grid_16">
							<div class="wrapper">
								<span style="position: absolute;">
									<a class="hidden" id="content" name="content">Page Content</a>
								</span>
								<h1>
									Thank you for applying, you will be contacted shortly.<br>
								</h1>
								<div style="text-align:center;">
									<em>Redirecting in <span id="box">10</span> seconds back to the ECS Department Homepage...</em>
								</div>
								<hr class="line">
									<div style="clear: both;"></div>
							</div>
						</div>
						<div style="clear: both;"></div>
					</div><br><br>
					<div class="footer" style="clear: both;">
						<p>
							California State University, Sacramento | 6000 J Street | Sacramento, CA 95819 | (916) 278-7337
						</p>
					</div>
			</div>
		</div>
		<div class="bottomshadow"></div>
	</body>
</html>