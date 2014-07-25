<?php
/*********************************************************
 *					INSERT INTO PROJECT                  *                  
 *********************************************************/

/*****Set variables from posted HTML form data*****/
$account_name=mysql_real_escape_string($_POST['account_name']);
$disk_quota=mysql_real_escape_string($_POST['disk_quota']);
$major=mysql_real_escape_string($_POST['major']);
$group=mysql_real_escape_string($_POST['group']);
$project_advisor=mysql_real_escape_string($_POST['project_advisor']);
$advisor_email=mysql_real_escape_string($_POST['advisor_email']);
$shell=mysql_real_escape_string($_POST['shell']);
$num_students=mysql_real_escape_string($_POST['num_students']);
$num_semesters=mysql_real_escape_string($_POST['num_semesters']);
$comments=mysql_real_escape_string($_POST['comments']);
$status=mysql_real_escape_string('Pending');

//set numseters equal to num_semesters_other, if more than 2
if ($num_semesters != 1 || $num_semesters != 2)
{ $num_semesters = mysql_real_escape_string($_POST['num_semesters_other']); }

//remove *s so shell can successfully be inserted into project database
if ($shell == '***Bash***') { $shell = 'Bash'; }
else if ($shell == '***Ksh***') { $shell = 'Ksh'; }
else if ($shell == '***Sh***') { $shell = 'Sh'; }
else if ($shell == '***Tcsh***') { $shell = 'Tcsh'; }

/*****Connects to Server*****/
$con=mysqli_connect("131-server.ecs.csus.edu","root","have.phun","project");
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

/*****Insert Project Account Info*****/
$sql="INSERT INTO project_info (projName, quota, groupName, dateExpires, status, advisor_name, advisor_email, shell)
VALUES
('$account_name', '$disk_quota', '$group', '$expire', 'Pending', '$project_advisor', '$advisor_email', '$shell')";

if (!mysqli_query($con,$sql))
{
	die('Error: ' . mysqli_error($con));
}

//insert comments into history, if any
if ($comments != "")
{
	$today = date("Y-m-d");
	$today = mysql_real_escape_string($today);
	
	$sql="INSERT INTO project_history (hisDetail, projName, hisShortDesc, hisDate)
	VALUES
	('$comments', '$account_name', 'Application Comments', '$today')";


	if (!mysqli_query($con,$sql))
	{
		die('Error: ' . mysqli_error($con));
	}
}

/*****Inserts student's contact Information*****/
$studentInfo = "";	
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
	
	$sql="INSERT INTO project_people (deptMajor, pNameLast, pNameFirst, phoneNum, email, projName)
	VALUES
	('$maj', '$lname', '$fname', '$phone', '$email', '$account_name')";

	if (!mysqli_query($con,$sql))
	{
		die('Error: ' . mysqli_error($con));
	}
}

mysqli_close($con); //close connection


/*****SENDS EMAIL TO ADMIN*****/
mysql_connect('131-server.ecs.csus.edu', 'root', 'have.phun'); // connects to server
mysql_select_db('project'); // selects a database
$count = mysql_query("SELECT COUNT(`status`) FROM `project_info` WHERE `status`='Pending'"); // Counts Pending accounts
$status = mysql_result($count, 0);
set_include_path(get_include_path() . PATH_SEPARATOR . 'php/phpseclib0'); // Includes things in phpseclib0 that is necessary to ssh
include('Net/SSH2.php');
$ssh = new Net_SSH2('athena.ecs.csus.edu'); //starting the ssh connection to localhost
if (!$ssh->login('131win9', 'roezyuzf')) { //if you can't log on...
	exit('Login Failed');
}
date_default_timezone_set('America/Los_Angeles'); // Sets time to PDT
$timeStamp = date('D M d h:i:s Y');// Gets the month, date, time, and year.
$horizontal = '________________________________________________________'; // Horizontal line use to divide the section in the email
$ssh->exec('cd ..');
/******************************************************************************
 *This sends the email with all the content, it will not work if it's divided.* 
 *ADMIN EMAIL is added at the end of this line------------------------------->*
 *Current Email Added: lynne@ecs.csus.edu                                     *
 ******************************************************************************/
$ssh->exec('echo "\n\nHello,\n\nYou have received a new application for a Project Account.\n\nPending Project Account Applications: '.$status.'\n'.$horizontal.'\n\nUser submitted the following data on '.$timeStamp.':\nAccount Name: '.$account_name.'\nDisk Quota: '.$disk_quota.'\nMajor: '.$major.'\nProject Advisor: '.$project_advisor.'\nAdvisor Email: '.$advisor_email.'\nAccount Type: '.$shell.'\nNumber of Students: '.$num_students.'\nNumber of Semesters: '.$num_semesters.'\nComments: '.$comments.'\n'.$horizontal.'\n\n'.$studentInfo.'Have a good day!" | mailx -r "noreply" -s "New Project Account Applicant" "lynne@ecs.csus.edu"');
mysql_close(); // Close Connection
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

