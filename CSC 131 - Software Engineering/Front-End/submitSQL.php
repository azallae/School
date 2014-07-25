<?php
/*********************************************************
 *					INSERT INTO DATAMAN                  *
 *********************************************************/

/*****Set variables from posted HTML form data*****/
$database_name=mysql_real_escape_string($_POST['database_name']);
$major=mysql_real_escape_string($_POST['major']);
$group=mysql_real_escape_string($_POST['group']);
$project_advisor=mysql_real_escape_string($_POST['project_advisor']);
$advisor_email=mysql_real_escape_string($_POST['advisor_email']);
$num_students=mysql_real_escape_string($_POST['num_students']);
$num_semesters=mysql_real_escape_string($_POST['num_semesters']);
$host=mysql_real_escape_string($_POST['mysql_host_location']);
$comments=mysql_real_escape_string($_POST['comments']);
$status=mysql_real_escape_string('Pending');

//set numseters equal to num_semesters_other, if more than 2
if ($num_semesters != 1 || $num_semesters != 2)
{ $num_semesters = mysql_real_escape_string($_POST['num_semesters_other']); }

/*****The database requires 'remote' or 'localhost'*****/
if ($host == '%') {	$host = mysql_real_escape_string('remote'); }

//set permissions for insertion into dataman database
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


$con=mysqli_connect("131-server.ecs.csus.edu","saveInfo","NewAccounts!!","dataman");
/*****Check connection*****/
if (mysqli_connect_errno())
{
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

/*****Finds Expiring Dates*****/
$todayDate= date("m");
$year = date("Y");
if($todayDate >= 1 && $todayDate <= 7){ // Applying during Spring semester
	if($num_semesters == 1){
		$tmpOne = gregoriantojd ( 7, 31, $year );
		$tmpOne = jdtogregorian($tmpOne);
		$tmpOne = date('Y-m-d', strtotime($tmpOne));
		$expire = mysql_real_escape_string($tmpOne);
	}else{
		for($i = 0; $i < $num_semesters-1; $i++){
			if($i % 2 == 0){
				$tmpTwo = gregoriantojd ( 12, 31, $year);
				$tmpTwo = jdtogregorian($tmpTwo);
				$tmpTwo = date('Y-m-d', strtotime($tmpTwo));
				$expire = mysql_real_escape_string($tmpTwo);
			}else{
				$year++;
				$tmpThree = gregoriantojd ( 7, 31, $year);
				$tmpThree = jdtogregorian($tmpThree);
				$tmpThree = date('Y-m-d', strtotime($tmpThree));
				$expire = mysql_real_escape_string($tmpThree);
			}
		}
	}
}else{ // Applying during Fall semester
	if($num_semesters == 1){
	$tmpFour = gregoriantojd ( 12, 31, $year );
	$tmpFour = jdtogregorian($tmpFour);
	$tmpFour = date('Y-m-d', strtotime($tmpFour));
	$expire = mysql_real_escape_string($tmpFour);
	}else{
		for($i = 0; $i < $num_semesters-1; $i++){
			if($i % 2 == 0){
			    $year++;
				$tmpFive = gregoriantojd ( 7, 31, $year);
				$tmpFive = jdtogregorian($tmpFive);
				$tmpFive = date('Y-m-d', strtotime($tmpFive));
				$expire = mysql_real_escape_string($tmpFive);
			}else{
				$tmpSix = gregoriantojd ( 12, 31, $year);
				$tmpSix = jdtogregorian($tmpSix);
				$tmpSix = date('Y-m-d', strtotime($tmpSix));
				$expire = mysql_real_escape_string($tmpSix);
			}
		}
	}
}

/*****Insert mysql database info*****/
$sql="INSERT INTO dataman_database (projName, server, dateExpires, status, advisor_name, advisor_email, groupName)
VALUES
('$database_name', '$host', '$expire', '$status', '$project_advisor', '$advisor_email', '$group')";

if (!mysqli_query($con,$sql))
{
	die('Error: ' . mysqli_error($con));
}

/*****Insert mysql permissions*****/
if ($host == 'localhost') {	$host = mysql_real_escape_string('local'); }
$sql="INSERT INTO dataman_dbaccounts (DBAccountUName, projName, db_host, db_alter, db_index, db_reference, db_drop, db_create, db_delete, db_update, db_insert, db_select)
VALUES
('$database_name', '$database_name', '$host', '$alter', '$index', '$ref', '$drop', '$create', '$delete', '$update', '$insert', '$select')";

if (!mysqli_query($con,$sql))
{
	die('Error: ' . mysqli_error($con));
}

/*****If there are comments, insert into history*****/
if ($comments != "")
{
	$today = date("Y-m-d");
	$today = mysql_real_escape_string($today);

	$sql="INSERT INTO dataman_history (hisDetail, projName, hisShortDesc, hisDate)
	VALUES
	('$comments', '$database_name', 'Application Comments', '$today')";

	if (!mysqli_query($con,$sql))
	{
		die('Error: ' . mysqli_error($con));
	}
}

/*****Inserts Contact Information*****/
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

	if (!mysqli_query($con,$sql))
	{
		die('Error: ' . mysqli_error($con));
	}
}

mysqli_close($con); //close connection

/*****SENDS EMAIL TO ADMIN*****/
mysql_connect('131-server.ecs.csus.edu', 'saveInfo', 'NewAccounts!!'); // Connects to server
mysql_select_db('dataman'); // Selects a database
$count = mysql_query("SELECT COUNT(`status`) FROM `dataman_database` WHERE `status`='Pending'"); // Counts Pending accounts
$status = mysql_result($count, 0);
set_include_path(get_include_path() . PATH_SEPARATOR . 'php/phpseclib0'); // Includes things in phpseclib0 that is necessary to ssh
include('Net/SSH2.php');
$ssh = new Net_SSH2('athena.ecs.csus.edu'); //Starting the ssh connection to localhost
if (!$ssh->login('131win9', 'roezyuzf')) { // If you can't log on...
	exit('Login Failed');
}
date_default_timezone_set('America/Los_Angeles');// Sets time to PDT
$timeStamp = date('D M d h:i:s Y');

$ssh->exec('cd ..');
/******************************************************************************
 *This sends the email with all the content, it will not work if it's divided.* 
 *ADMIN EMAIL is added at the end of this line------------------------------->*
 *Current Email Added: lynne@ecs.csus.edu                                     *
 ******************************************************************************/
$ssh->exec('echo "Hello,\n\nYou have received a new application for a MySQL Database.\n\nTotal MySQL Databases awaiting activation: '.$status.'\n'.$horizontal.'\n\nUser submitted the following data on '.$timeStamp.':\nMajor: '.$major.'\nProject Advisor: '.$project_advisor.'\nAdvisor Email: '.$advisor_email.'\nNumber of Participants: '.$num_students.'\nNumber of Semester: '.$num_semesters.'\nDatabase Name: '.$database_name.'\nMysql Host Location: '.$host.'\nMysql Permissions: '.$permissions.'\nComments: '.$comments.'\n'.$horizontal.'\n\n'.$studentInfo.'Have a good day!" | mailx -r "noreply" -s "New MySQL Database Applicant" "lynne@ecs.csus.edu"');
mysql_close(); // close connection

?>
<!-----The Submit Page----->
<!-----This webpage is similar to the webpage seen after logging out from my.csus.edu----->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- saved from url=(0027)http://www.csus.edu/logout/ -->
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml" lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link charset="UTF-8" href="./Logout_files/reset-min.css" media="all" rel="stylesheet" type="text/css">
    <link charset="utf-8" href="./Logout_files/seafoam2.css" media="all" rel="stylesheet" title="Default Styles" type="text/css">
    <link charset="UTF-8" href="./Logout_files/private.css" media="all" rel="stylesheet" type="text/css">
	<!-----Ridirect User Back To Homepage----->
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