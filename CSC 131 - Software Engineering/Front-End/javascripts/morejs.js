/***********************************
functions for selectAcct.htm
***********************************/

//hides the dropdown question that leads to DualForm
function HidePart2() {
	var x = document.getElementById("lbl");
	var y = document.getElementById("yesORno");
	var z = document.getElementById("account_type");
					
	if (z.value == "Project" || z.value == "MySQL") //show it
	{
		//set the correct text to display
		if (z.value == "Project")
		{
			lbl.innerHTML = "Do you also want a MySQL Database?"
		}
		else //user chose MySQL
		{
			lbl.innerHTML = "Do you also want a Project Account?"
		}
		//show the text and dropdown
		y.selectedIndex = 0;
		x.style.display = "";
		y.style.display = "";
	}
	else //Project or MySQL not selected, hide text and dropdown
	{
		y.selectedIndex = 0;
		x.style.display = "none";
		y.style.display = "none";
	}
}

//hides the text and dropdown when the form loads, it only needs to be visible when Project or MySQL selected
function mkHidden() {
	var x = document.getElementById("lbl");
	var y = document.getElementById("yesORno");
	y.selectedIndex = 0;
	x.style.display = "none";
	y.style.display = "none";
}

//happens when the form is submitted, redirects to the correct page
function chkSelected() {
	var x = document.getElementById("account_type");
	var y = document.getElementById("yesORno");
	
	if (x.value == "Project") 
	{
		if (y.value == "Yes") { location.href = "DualForm.php";	}
		else {	location.href = "ProjectAcct.php";  }
	}
	else if (x.value == "MySQL") {
		if (y.value == "Yes") { location.href = "DualForm.php";	}
		else {	location.href = "MySQLacct.php";  }
	}
	else if (x.value == "None") {
		//nothing was selected so do nothing
	}
	else { //something other than Project or MySQL selected so direct to normal page
		var f = document.getElementById("select_account");
		f.elements[0].value = x.value;
		f.submit();
	}
}
/***********************************
End of functions for selectAcct.htm
***********************************/

/***********************************
functions for the forms
***********************************/

//if "Other" selected for number of semesters, enable it and focus to it
function enableOther() {
	var otherTbox = document.getElementById("num_semesters_other");
	var semesters = document.getElementById("num_semesters");
	
	if(semesters.selectedIndex == 3 ) 
	{ 
		otherTbox.disabled = false; 
		otherTbox.focus(); 
	}
	else //if "Other" not selected, clear num_semesters_other and disable it (also happens on form load)
	{
		otherTbox.text = "";
		otherTbox.value = "";
		otherTbox.disabled = true;
	}
}

//auto-selects the "Other" radio button for when user clicks a MySQL permission checkbox
function otherRB()
{
	document.getElementById("other").checked = true;
	
}

//removes the invalid account name red text when the field is changed from a non-valid entry
function hideRed()
{
	document.getElementById("tmp").innerHTML = '<span style="color:blue"></span>';
}

//removes the invalid email red text when the field is changed from something that was invalid
function hideRed2()
{
	document.getElementById("tmp_email").innerHTML = '<span style="color:black">@ecs.csus.edu</span>';
}

/***********************************
functions for the forms
***********************************/