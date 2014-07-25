
<!-- saved from url=(0094)https://www.ecs.csus.edu/webApps/useraccount_app/index.cgi?form_id=new_student_project_account -->

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>ECS Computing Services Request for Computer User Account / Action –– New Student Project Account and MySQL Database</title>
		<link rel="stylesheet" type="text/css" href="./javascripts/useraccount.css">
		<script language="javascript" type="text/javascript" src="./javascripts/useraccount.js"></script>
		<!--------------------------------------------->
		<!--------------------------------------------->
		<!--------------------------------------------->
		<!--Functions added for 131 project-->
		<!--------------------------------------------->
		<!--------------------------------------------->
		<!--------------------------------------------->
		<script type="text/javascript" src="jquery/jquery.js"></script>
		<script type="text/javascript" src="jquery/DualAcctNameCheck.js"></script>
		<script type="text/javascript" src="jquery/ECSemailValidate.js"></script>
		<script type="text/javascript" src="javascripts/morejs.js"></script>
		<!--------------------------------------------->
		<!--------------------------------------------->
		<!--------------------------------------------->
		<!--End of scripts for 131 project-->
		<!--------------------------------------------->
		<!--------------------------------------------->
	</head>
	<body onload="frmFocus(); enableOther();"> <!--added enableOther() here, see morejs.js-->
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
						<strong>New Student Project Account and MySQL Database</strong>
					</td>
				</tr>
				<tr>
					<td height="10">
						<img alt="Spacer Image" src="./javascripts/spacer.gif" width="1" height="1">
					</td>
				</tr>
				<tr>
					<td>
						<!--onsubmit changed to a more specific validate form function, meant to deal solely with the dual form form-->
						<form name="studentDualAccountForm1" method="post" onsubmit="return validateStudentDualAccountForm1(document.studentDualAccountForm1);" action="DualForm2.php"> 
							<table width="590" border="0">
								<tbody>
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
														<td colspan="2" nowrap="" height="25" valign="top" class="fieldLabel">
															<b>Account Information</b>
														</td>
													</tr>
													<tr>
														<td colspan="2" class="fieldLabel">
															<font color="red"><b>Note:</b> Project account names cannot be a user's personal account or<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; derivation of a single user's name.</font>
														</td>
													</tr> 
													<tr>
														<td colspan="2" height="25">
															<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
														</td>
													</tr>		
													<!-------------------->
													<!-------------------->
													<!-------------------->
													<!--New field for the email address to be used for verification that the applicant is allowed to create an account/database-->
													<tr>
														<td class="fieldLabel" valign="middle" nowrap="">ECS Email Address:</td>
														<td class="formField" valign="middle">
															<label for="validation_email">
																<input id="validation_email" name="validation_email" autocomplete="off" onkeyup="hideRed2()" type="text" size="20" maxlength="50">
															</label>&nbsp;
															<span id="tmp_email">@ecs.csus.edu</span>
														</td>
													</tr>
													<tr>
														<td colspan="2" height="10">
															<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
														</td>
													</tr>
													<!-------------------->
													<!-------------------->
													<!-------------------->															
													<tr>
														<td class="fieldLabel" valign="middle" nowrap="">Account/Database Name:</td>
															<td class="formField" valign="middle">
																<label for="account_name">
																	<input id="account_name" name="account_name" autocomplete="off" onkeyup="hideRed()" type="text" size="8" maxlength="8">
																</label>&nbsp;
																<span id="tmp"/>
															</td>
														</tr>
														<td></td><td><div>(Gaia Account, 3-8 chars.)</div></td>
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="middle" nowrap="">Disk Quota:</td>
															<td class="formField" valign="middle">
																<label for="disk_quota">
																	<input id="disk_quota" name="disk_quota" type="text" value="1500" size="4" maxlength="4">&nbsp;
																	<span id="fieldRequirements"></span> 
																</label>(in MB)
															</td>
														</tr>
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<!-------------------->
														<!-------------------->
														<!-------------------->
														<!--Major dropdown removed here, inserted under num_students-->
														<!-------------------->
														<!-------------------->
														<!-------------------->
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="middle" nowrap="">Project Advisor:</td>
															<td class="formField" valign="middle">
																<label for="project_advisor">
																	<input type="text" id="project_advisor" name="project_advisor" size="15" maxlength="50">
																</label>
															</td>
														</tr>
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="middle" nowrap="">Advisor Email:</td>
															<td class="formField" valign="middle">
																<label for="advisor_email">
																	<input type="text" id="advisor_email" name="advisor_email" size="30" maxlength="50">
																</label>
															</td>
														</tr>
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="middle" nowrap="">Unix Shell:</td>
															<td class="formField" valign="middle">
																<label for="shell">
																	<select id="shell" name="shell">
																		<option value="Csh">Csh</option>
																		<option value="***Bash***">Bash</option>
																		<option value="***Ksh***">Ksh</option>
																		<option value="***Sh***">Sh</option>
																		<option value="***Tcsh***">Tcsh</option>
																		&nbsp;&nbsp;(csh is standard)
																	</select>
																</label>
															</td>
														</tr>
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel">Number of Participants:</td>
															<td class="fieldLabel">
																<label for="num_students">
																	<input id="num_students" name="num_students" type="text" size="2" maxlength="2">
																</label> (1-10 Students)
															</td>
														</tr>
														<!-------------------->
														<!-------------------->
														<!--Major reinserted here-->
														<!-------------------->
														<!-------------------->		
														<tr>
															<td class="fieldLabel" valign="middle" nowrap="" id="majorLBL">Major:</td>
															<td class="formField" valign="middle">
																<label for="major">
																	<select id="major" name="major">
																		<option selected="" value="Various">Select Major: </option>
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
														</tr>
														<!------------------>
														<!------------------>
														<!--End of insertion-->
														<!------------------>
														<!------------------>
														
														<!------------------>
														<!------------------>
														<!--Added Group dropdown menu-->
														<!------------------>
														<!------------------>		
														<tr>
															<td class="fieldLabel" valign="middle" nowrap="" id="groupLBL">Group:</td>
															<td class="formField" valign="middle">
																<label for="group">
																	<select id="group" name="group">
																		<option selected="" value="Student">Student</option>
																		<option value="Class">Class</option>
																		<option value="Clubs">Clubs</option>
																		<option value="Departmental">Departmental</option>
																		<option value="CSC 122">CSC 122</option>
																		<option value="CSC 177">CSC 177</option>
																		<option value="Faculty/Staff">Faculty/Staff</option>
																		<option value="Test">Test (Hera)</option>
																		<option value="ECS Computing">ECS Department</option>
																		<option value="Permanent">Permanent</option>
																		<option value="Meilu Lu">Meilu Lu</option>
																	</select>
																</label>
															</td>
														</tr>
														<!------------------>
														<!------------------>
														<!--End of Group dropdown-->
														<!------------------>
														<!------------------>													
														<tr>
															<td colspan="2" height="25">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="top" nowrap="">How long will the account <br>be active?</td>
															<td class="formField" valign="middle">
																<label for="num_semesters">
																	<select id="num_semesters" name="num_semesters" onclick="enableOther()">
																		<option value="Select Number of Semesters">Select Number of Semesters</option>
																		<option value="1">One Semester</option>
																		<option value="2">Two Semesters</option>
																		<option value="Other">Other</option>
																	</select>
																</label>
																<br><br>Other: 
																<label for="num_semesters_other">
																	<input type="text" id="num_semesters_other" name="num_semesters_other" size="2" maxlength="2">
																</label> Semesters.
															</td>
														</tr>
														<!---------------------------------------------------------------------------------------------------------->
														<!---------------------------------------------------------------------------------------------------------->
														<!----------------------ADDED FROM SQL DATABASE APPLICATION------------------------------------------------------->
														<!---------------------------------------------------------------------------------------------------------->
														<!---------------------------------------------------------------------------------------------------------->
														<!---------------------------------------------------------------------------------------------------------->
														<tr>
															<td colspan="2" height="40">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td colspan="2">
																<img alt="Black Background" src="./javascripts/black.gif" width="500" height="1">
															</td>
														</tr>
														<tr>
															<td colspan="2" nowrap="" height="25" valign="top" class="fieldLabel">
																<b>Database Information</b>
															</td>
														</tr>		
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="top" nowrap="">MySQL Host Location: </td>
															<td class="formField" valign="middle">
																<label for="localhost">
																	<input type="radio" id="localhost" name="mysql_host_location" value="localhost">
																</label>localhost *<br>
																<label for="anyhost">
																	<input type="radio" id="anyhost" name="mysql_host_location" value="%" checked="checked">
																</label>% (any host) **<br><br>
																<span style="font-style: italic;">
																	<b>*</b> Localhost: Must be logged into assigned MySQL server - i.e. athena<br><br>
																	<b>**</b> % (Any Host): Must use -h &lt;server name&gt; - i.e. -h athena; commonly used for web applications
																</span>
															</td>
														</tr>
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="top" nowrap="">Your Permissions: </td>
															<td class="formField" valign="middle">
																<label for="all">
																	<input type="radio" id="all" name="permissions_radio" onclick="checkAll(this.form, &#39;mysql_permissions[]&#39; );" value="All">
																</label>All<br>
																<label for="standard">
																	<input type="radio" id="standard" name="permissions_radio" onclick="uncheckAll(this.form, &#39;mysql_permissions[]&#39;); checkElements( this.form,
																	&#39;mysql_permissions[]&#39;, [&#39;SELECT&#39;, &#39;INSERT&#39;, &#39;UPDATE&#39;, &#39;DELETE&#39;] );" checked="" value="Standard">
																</label>Standard (SELECT, INSERT, UPDATE, DELETE)<br>
																<label for="other">
																	<input type="radio" id="other" name="permissions_radio" onclick="uncheckAll(this.form, &#39;mysql_permissions[]&#39;);" value="Other">
																</label>Other (Please specify):<br><br>
																<table border="0" cellpadding="2" cellspacing="0">
																	<tbody>
																		<!-- (checkboxes below) made into array for easier php manipulation and added otherRB() onclick event (see morejs.js)-->
																		<tr>
																			<td>
																				<label for="alter">
																					<input type="checkbox" id="alter" name="mysql_permissions[]" value="ALTER" onclick="otherRB();">
																				</label>ALTER
																			</td>
																			<td>
																				<label for="insert">
																					<input type="checkbox" id="insert" name="mysql_permissions[]" value="INSERT" checked="checked" onclick="otherRB();">
																				</label>INSERT
																			</td>
																			<td>
																				<label for="create">
																					<input type="checkbox" id="create" name="mysql_permissions[]" value="CREATE" onclick="otherRB();">
																				</label>CREATE
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<label for="delete">
																					<input type="checkbox" id="delete" name="mysql_permissions[]" value="DELETE" checked="checked" onclick="otherRB();">
																				</label>DELETE
																			</td>
																			<td>
																				<label for="select">
																					<input type="checkbox" id="select" name="mysql_permissions[]" value="SELECT" checked="checked" onclick="otherRB();">
																				</label>SELECT
																			</td>
																			<td>
																				<label for="drop">
																					<input type="checkbox" id="drop" name="mysql_permissions[]" value="DROP" onclick="otherRB();">
																				</label>DROP
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<label for="index">
																					<input type="checkbox" id="index" name="mysql_permissions[]" value="INDEX" onclick="otherRB();">
																				</label>INDEX
																			</td>
																			<td>
																				<label for="update">
																					<input type="checkbox" id="update" name="mysql_permissions[]" value="UPDATE" checked="checked" onclick="otherRB();">
																				</label>UPDATE
																			</td>
																			<td>
																				<label for="references">
																					<input type="checkbox" id="references" name="mysql_permissions[]" value="REFERENCES" onclick="otherRB();">
																				</label>REFERENCES
																			</td>
																		</tr>
																	</tbody>
																</table>
															</td>
														</tr>
														<!---------------------------------------------------------------------------------------------------------->
														<!---------------------------------------------------------------------------------------------------------->
														<!-------------------END OF SQL DATABASE ADDED FORM ITEMS------------------------------------------------------------------->
														<!---------------------------------------------------------------------------------------------------------->
														<!---------------------------------------------------------------------------------------------------------->
														<tr>
															<td colspan="2" height="10">
																<img alt="Spacer Image" src="./javascripts/spacer.gif" height="1" width="1">
															</td>
														</tr>
														<tr>
															<td class="fieldLabel" valign="top" nowrap="">Comments:</td>
															<td class="formField" valign="top">
																<label for="comments">
																	<textarea id="comments" name="comments" cols="25" rows="5"></textarea>
																</label>
															</td>
														</tr>
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
															<input type="submit" name="Submit" value="Continue &gt;&gt;"></td><td width="25">
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
							<input type="hidden" name="form_id" value="new_student_project_account2">
						</form>
					</td>
				</tr>
				<tr>
					<td align="center" height="5"><img alt="Spacer Image" src="./javascripts/spacer.gif" width="1" height="1"></td>
				</tr>
				<tr>
					<td>
						<img alt="Black Background" src="./javascripts/black.gif" width="590" height="1">
					</td>
				</tr>
				<tr>
					<td align="center" id="appFooter">
						<a href="https://www.ecs.csus.edu/webApps/useraccount_app/index.cgi">main menu</a>&nbsp;&nbsp;|&nbsp;&nbsp;
						<a href="http://www.ecs.csus.edu/index.php?content=info">computing services</a>&nbsp;&nbsp;|&nbsp;&nbsp;
						<a href="http://www.ecs.csus.edu/">ecs home</a>
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>