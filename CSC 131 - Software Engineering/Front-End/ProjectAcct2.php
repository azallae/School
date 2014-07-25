
<!-- saved from url=(0058)https://www.ecs.csus.edu/webApps/useraccount_app/index.cgi -->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>ECS Computing Services Request for Computer User Account / Action –– New Student Project Account</title>
		<link rel="stylesheet" type="text/css" href="./javascripts/useraccount.css">
		<script language="javascript" type="text/javascript" src="./javascripts/useraccount.js"></script>	
	</head>
	<body onload="frmFocus();">
		<table width="590" border="0" align="center">
			<tbody>
				<tr>
					<td>
						<table align="center" border="0" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td align="center">
										<span id="appHeading">CALIFORNIA STATE UNIVERSITY, SACRAMENTO</span>
									</td>
								</tr>
								<tr>
									<td align="center">
										<span id="appHeading">COLLEGE OF ENGINEERING AND COMPUTER SCIENCE</span>
									</td>
								</tr>
								<tr>
									<td height="10">
										<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
									</td>
								</tr>
								<tr>
									<td width="590" align="center">
										<span id="appHeading">
											<b>REQUEST FOR COMPUTER USER ACCOUNT / ACTION</b>
										</span>
									</td>
								</tr>
								<tr>
									<td align="center">
										<span id="appHeadingSmall">THIS IS NOT A SACLINK OR DIAL-IN ACCOUNT</span>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td height="25">
						<img alt="Spacer Image" src="./javascripts/spacer.gif" width="1" height="1">
					</td>
				</tr>
				<tr>
					<td>
						<img alt="Black Background" src="./javascripts/black.gif" width="590" height="1">
					</td>
				</tr>
				<tr>
					<td id="formHeading">
						<strong>New Student Project Account</strong>
					</td>
				</tr>
				<tr>
					<td height="10">
						<img alt="Spacer Image" src="./javascripts/spacer.gif" width="1" height="1">
					</td>
				</tr>
				<tr>
					<td>
						<form name="studentProjectAccountForm2" method="post" onsubmit="return validateStudentProjectAccountForm2(document.studentProjectAccountForm2);" action="submitProj.php"> 
							<!-- HTML HIDDEN FIELDS GO HERE -->
							<input type="hidden" name="form_id" value="new_student_project_account2">
							<input type="hidden" name="account_name" value="<?php echo $_POST["account_name"]; ?>">
							<input type="hidden" name="disk_quota" value="<?php echo $_POST["disk_quota"]; ?>">
							<input type="hidden" name="major" value="<?php echo $_POST["major"]; ?>">
							<input type="hidden" name="group" value="<?php echo $_POST["group"]; ?>">
							<input type="hidden" name="project_advisor" value="<?php echo $_POST["project_advisor"]; ?>">
							<input type="hidden" name="advisor_email" value="<?php echo $_POST["advisor_email"]; ?>">
							<input type="hidden" name="shell" value="<?php echo $_POST["shell"]; ?>">
							<input type="hidden" name="num_students" value="<?php echo $_POST["num_students"]; ?>">
							<input type="hidden" name="num_semesters" value="<?php echo $_POST["num_semesters"]; ?>">
							<input type="hidden" name="comments" value="<?php echo $_POST["comments"]; ?>">
							<input type="hidden" name="num_semesters_other" value="<?php if ($_POST["num_semesters"]==1) { echo '1'; } else if ($_POST["num_semesters"]==2) { echo '2'; } else { echo $_POST["num_semesters_other"]; } ?>">
							<table width="590" border="0">
								<tbody>
									<!--posted data from previous form for display and reposting-->
									<tr>
										<td width="25">
											<img alt="Spacer Image" src="./javascripts/spacer.gif" width="1" height="1">
										</td>
										<td width="565">
											<table width="100%" border="0">
												<tbody>
													<tr>
														<td colspan="2">
															<img alt="Black Background" src="./javascripts/black.gif" width="500" height="1">
														</td>
													</tr>
													<tr>
														<td colspan="2" class="fieldLabel">
															<b>Account Information</b>
														</td>
													</tr>
													<tr>
														<td colspan="2" height="10">
															<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
														</td>
													</tr>
													<tr>
														<td colspan="2" class="content">Account name: <?php echo $_POST["account_name"]; ?>
															<input type="hidden" name="account_name" value="<?php echo $_POST["account_name"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" class="content">Disk quota: <?php echo $_POST["disk_quota"]; ?>
															<input type="hidden" name="disk_quota" value="<?php echo $_POST["disk_quota"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" class="content">Project advisor: <?php echo $_POST["project_advisor"]; ?>
															<input type="hidden" name="project_advisor" value="<?php echo $_POST["project_advisor"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" class="content">Advisor email: <?php echo $_POST["advisor_email"]; ?>
															<input type="hidden" name="advisor_email" value="<?php echo $_POST["advisor_email"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" class="content">Unix Shell: <?php echo $_POST["shell"]; ?>
															<input type="hidden" name="shell" value="<?php echo $_POST["shell"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" class="content">Number of Participants: <?php echo $_POST["num_students"]; ?>
															<input type="hidden" name="num_students" id="num_students" value="<?php echo $_POST["num_students"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" class="content">Major: <?php echo $_POST["major"]; ?>
															<input type="hidden" name="major" value="<?php echo $_POST["major"]; ?>">
														</td>
													</tr>
													<tr>
														<td colspan="2" class="content">Group: <?php echo $_POST["group"]; ?>
															<input type="hidden" name="group" value="<?php echo $_POST["group"]; ?>">
														</td>
													</tr>
													<tr>
														<td colspan="2" class="content">Number of Semesters: <?php echo $_POST["num_semesters"]; ?>
															<input type="hidden" name="num_semesters" value="<?php echo $_POST["num_semesters"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" class="content">Comments: <?php echo $_POST["comments"]; ?>
															<input type="hidden" name="comments" value="<?php echo $_POST["comments"]; ?>">
														</td>
													</tr>	
													<tr>
														<td colspan="2" height="25">
															<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
														</td>
													</tr>
													<tr>
														<td colspan="2">
															<img alt="Black Background" src="./javascripts/black.gif" height="1" width="500">
														</td>
													</tr>
													<tr>
														<td colspan="2" class="fieldLabel">
															<b>Please fill in the contact information for each student:</b>
														</td>
													</tr>
													<tr>
														<td colspan="2" height="25">
															<img alt="Spacer Image" src="./javascripts/spacer.gif">
														</td>
													</tr>
													<!--builds up the required student info input fields, based on num_students-->									
													<?php 
														for($tr=1;$tr<=$_POST["num_students"];$tr++){ 
															echo '<!-- INFORMATION FOR ONE STUDENT -->';
															echo '<tr><td colspan="2"><img alt="Black Background" src="./javascripts/black.gif" height="1" width="350"></td></tr>'; 
															//prints out which student the data is for
															echo '<tr><td colspan="2" class="fieldLabel"><b><i>Student #'.$tr.'</i></b></td></tr>';
															echo '<tr><td colspan="2" height="10"><img alt="Spacer Image" src="./javascripts/spacer.gif"></td></tr>';
															//prints out each student's first name
															echo '<tr><td class="fieldLabel" valign="middle" nowrap="">First Name</td><td class="formField" valign="middle"><input name="fname'.$tr.'" type="text" size="20" maxlength="40"></td></tr>';
															echo '<tr><td colspan="2" height="10"><img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1"></td></tr>';
															//prints out each student's last name
															echo '<tr><td class="fieldLabel" valign="middle" nowrap="">Last Name:</td><td class="formField" valign="middle"><input name="lname'.$tr.'" type="text" size="20" maxlength="40"></td></tr>';
															echo '<tr><td colspan="2" height="10"><img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1"></td></tr>';
															//prints out each student's phone number
															echo '<tr><td class="fieldLabel" valign="middle" nowrap="">Phone (w/ area code):</td><td class="formField" valign="middle"><input name="phone'.$tr.'" type="text" size="12" maxlength="15"></td></tr>';
															echo '<tr><td colspan="2" height="10"><img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1"></td></tr>';
															//prints out each student's contact email address
															echo '<tr><td class="fieldLabel" valign="middle" nowrap="">Email Address:</td><td class="formField" valign="middle"><input name="email'.$tr.'" type="text" size="20" maxlength="45">&nbsp;<span id="fieldRequirements">(Must be a valid email)</span></td></tr>';
															//if more than 1 student, populate a "major" dropdown for each student
															if ($_POST["num_students"] > 1) {
																echo '<tr><td colspan="2" height="10"><img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1"></td></tr>';				
																echo '<tr>
																		<td class="fieldLabel" valign="middle" nowrap="" id="majorLBL">Major:</td>
																		<td class="formField" valign="middle">
																			<label for="major">
																				<select id="major'.$tr.'" name="major'.$tr.'">
																					<option selected="">Select Major: </option>
																					<option value="Civil Engineering">Civil Engineering</option>
																					<option value="Computer Engineering">Computer Engineering</option>
																					<option value="Computer Science">Computer Science</option>
																					<option value="Construction Management">Construction Management</option>
																					<option value="Electrical and Electronic Engineering">Electrical and Electronic Engineering</option>
																					<option value="Engineering">Engineering</option>
																					<option value="Mechanical Engineering">Mechanical Engineering</option>
																					<option value="Other Colleges at CSUS">Other Colleges at CSUS</option>
																					<option value="Offcampus">Off Campus</option>
																				</select>
																			</label>
																		</td>
																	</tr>';
															}
															echo '<tr><td colspan="2" height="25"><img alt="Spacer Image" src="./javascripts/spacer.gif"></td></tr>';
															echo '<!-- END INFORMATION FOR ONE STUDENT -->';	
														} 
													?>
													<input type="hidden" name="endOfStudents">
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="2" height="10"><img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1"></td>
									</tr>
									<tr>
										<td height="10">&nbsp;</td>
										<td>
											<table border="0" cellpadding="0" cellspacing="0">
												<tbody>
													<tr>
														<td>
															<input type="submit" name="Submit" value="Send Application">
														</td>
														<td width="25">
															<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
														</td>
														<td>
															<input type="button" value="Start Over" onclick="resetForm(this.form);">
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
						</form>	
					</td>
				</tr>
				<tr>
					<td align="center" height="5">
						<img alt="Spacer Image" src="./javascripts/spacer.gif" width="1" height="1">
					</td>
				</tr>
				<tr>
					<td>
						<img alt="Black Background" src="./javascripts/black.gif" width="590" height="1">
					</td>
				</tr>
				<tr>
					<td align="center" id="appFooter">
						<a href="./javascripts/ProjectAcct2.htm">main menu</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="http://www.ecs.csus.edu/index.php?content=info">computing services</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="http://www.ecs.csus.edu/">ecs home</a>
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>