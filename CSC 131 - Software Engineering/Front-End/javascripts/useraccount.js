/********************************************************************
* File Name:   useraccount.js
*
* Description: This file contains all of the verification code for
*              the forms in the computing / network acccounts
*              application.
* 
* Programmer:  Sam Archer 
*              samarcher@sbcglobal.net
********************************************************************/


/* This is used as the beginning of the validation error message and should not
be changed at run time. */
topErrorMessage = "Please check the following fields for missing information:\n\n";

/********************************************************************
* Function:     frmFocus
*
* Description:  This javascript function sets focus to the first 
*               field that allows it (currently allows text and 
*               select-one.
*
* Parameters:   None.
*/
function frmFocus()
{
  var i = 0, 
    j = 0, 
    field, // a form element
    found; // boolean to exit loop
  
  for( i = 0; i < document.forms.length && !found ; i++ )
  {
    for( j = 0; j < document.forms[i].elements.length && !found; j++ )
    {
      field = document.forms[i].elements[j];

      found = ( field.type &&
	field.type == "text" || 
	field.type == "select-one" ||
	field.type == "select-multiple" ||
	field.type == "radio" ||
	field.type == "textarea"
      );
    }
  }

  if( found )
    field.focus();

  return;
} // frmFocus

/********************************************************************
* Function:     newWindow 
* Description: 
*/
function newWindow( url, winName, attribs )
{
	var aWin = window.open( url, winName, attribs );
	aWin.focus();
}


/********************************************************************
* Function:     resetForm
* Description:  This is event for the "Start Over" button on all the
*               forms. This was implemented as a function to keep
*               application behavior consistent between forms.
*               Params: theForm is a form object.
*/
function resetForm( theForm )
{
  if( confirm('Clear all information?') ) 
  { 
    theForm.reset(); 
    frmFocus();
	greyOut();
  }
}


/********************************************************************
* Function:    checkAll 
*
* Description: Checks all boxes in a checkbox group.
*
* Parameters:  theForm is the form object in which the checkbox 
*              resides. theCheckboxName is the name attribute of the 
*              checkbox.
*/
function checkAll( theForm, theCheckboxName )
{
  var i;

  for( i = 0; i < theForm.elements.length; i++ )
  {
    if( theForm.elements[i].name == theCheckboxName )
    {
      theForm.elements[i].checked = true;
    }
  }
}


/********************************************************************
* Function:    uncheckAll
*
* Description: Unchecks all elements of a checkbox.
*
* Parameters:  theForm is the form in which the checkbox resides.
*              theCheckboxName is the name of the checkbox for which
*              all elements should be unchecked.
*/
function uncheckAll( theForm, theCheckboxName )
{
  var i;

  for( i = 0; i < theForm.elements.length; i++ )
  {
    if( theForm.elements[i].type == 'checkbox' &&  
	theForm.elements[i].name == theCheckboxName )
    {
      theForm.elements[i].checked = false;
    }
  }
}


/********************************************************************
* Function:    checkElements
*
* Description: Checks a list of elements in a check box field by
*              their values.
*
* Parameters:  theForm is the form in which the checkbox resides.
*              theCheckboxName is the name of the checkbox (a string)
*              and theElements is an array of element values that 
*              specify which elements are to be checked.
*/
function checkElements( theForm, theCheckboxName, theElements )
{
  var i, j;

  for( i = 0; i < theForm.elements.length; i++ )
  {
    if( theForm.elements[i].name == theCheckboxName )
    {
      theCheckbox = theForm.elements[i];

      for( j = 0; j < theElements.length; j++ )
      {
	if( theCheckbox.value == theElements[j] )
	{
	  theCheckbox.checked = true;
	}
      }
    }
  }
}

/********************************************************************
* Function:    isBlank
*
* Description: Checks whether or not a field is blank.  
*
* Parameters:  theString is the string to check for "blankness" ;) 
*/
function isBlank( theString )
{
  var re = /^\s*$/;

  return( re.test( theString ) );
} // isBlank


/********************************************************************
* Function:     isValidName 
*
* Description:  Returns true if theName contains only letters, 
*               hyphens, and spaces, returns false otherwise.
*
* Parameters:   theName is a string containing a name to validate.
*/
function isValidName( theName )
{
  var theRegEx = /^[a-zA-Z\-.\' ]+$/;
  return ( theRegEx.test(theName) && 
           (! /^\s+$/.test( theName ) ) 
	 );
}

/********************************************************************
* Function:     isValidPhone
*
* Description:  Returns true if thePhoneNumber appears to be a 
*               valid phone number, false otherwise.
*               
* Parameters:   thePhoneNumber is a string containing the phone
*               number to be validated.
*/
function isValidPhone( thePhoneNumber )
{  
  theRegEx = /^\(?[\d]{3}\)?\s*-?\s*[\d]{3}-?[\d]{4}\s*$/;
  return ( theRegEx.test(thePhoneNumber) );
}

/********************************************************************
* Function:     isValidEmail
*
* Description:  Returns true if theEmailAddress appears to be a 
*               valid email address, returns false otherwise.
*
* Parameters:   theEmailAddress is a string containing the email
*               address to be validated.
*/
function isValidEmail( theEmailAddress )
{
  var isValid = true;
  var invalidChars = "/:,;#";
  var badChar;
  var atPos = theEmailAddress.indexOf("@", 1);
  var periodPos = theEmailAddress.indexOf(".", atPos);

  if (theEmailAddress == "")
    isValid = false;
  else
  {  
    // Something's in the field, let's make sure all the chars are valid:
    for (var i = 0; i < invalidChars.length; i++)
    {
      badChar = invalidChars.charAt(i);

      if ( theEmailAddress.indexOf(badChar,0) > -1 )
        isValid = false;
    }

    if (isValid)
    {
      // Characters are valid, but let's check their placement.
      if (atPos < 0)
        isValid = false;
      else if (theEmailAddress.indexOf("@", atPos + 1) > 0)
        isValid = false;
      else if (periodPos == -1)
        isValid = false;
      else if (periodPos + 3 > theEmailAddress.length)
        isValid = false;
      else if (periodPos == atPos + 1)
        isValid = false;
    }
  }
  
  return isValid;
}

/********************************************************************
* Function:     isValidDepartment
*
* Description:  Returns true if user has selected a department from
*               theDepartmentField select box.
*
* Parameters:   theDepartmentField is a select box object.
*/
function isValidDepartment( theDepartmentField )
{
  return (theDepartmentField.selectedIndex > 0);
}

/********************************************************************
* Function:     isValidMajor
*
* Description:  Returns true if user has selected a major from the 
*               theMajorField select box.
*
* Parameters:   theMajorField is a select box object.
*/
function isValidMajor( theMajorField )
{
  return (theMajorField.selectedIndex > 0);
}

/********************************************************************
* Function:     isValidAccountName
*
* Description:  Returns true if theAccountName contains 3-8 chars
*               of the following chars: a-z, A-Z, 0-9, _
*
* Parameters:   theAccountName is a unix account name to be 
*               validated.
*********************************************************************
*********************************************************************
*********************************************************************
*********************************************************************
*********************************************************************
* Edited by:    Cody Lanier
*
* Purpose:      Only letters (no symbols, no numbers)
*********************************************************************
*********************************************************************
*********************************************************************
*********************************************************************
********************************************************************/
function isValidAccountName( theAccountName )
{
  theRegEx = /^[a-zA-Z]{3,8}$/; // between 3 and 8 normal characters
  return ( theRegEx.test(theAccountName) );
}


/********************************************************************
* Function:     isValidSystemSelection
*
* Description:  Returns true if one of the two conditions are met,
*               false otherwise:
*
*               1) The sys_unix check box is checked, and a valid 
*                  shell type is selected from the shell select box
*
*               2) The sys_win check box is checked.
*
* Parameters:   theForm is the form object is a form containing 
*               a check box named sys_unix, a check box named sys_win
*               and a select box named shell.
*/
function isValidSystemSelection( theForm )
{
  return ( ( theForm.sys_unix.checked && isValidShell(theForm) ) || theForm.sys_win.checked );  
}

/********************************************************************
* Function:     isValidShell
*
* Description:  Returns true if a shell was selected from the shell
*               select box, returns false otherwise.
*
* Parameters:   theForm is a form object containing a select box
*               named shell.
*/
function isValidShell( theForm )
{
  return true;
}

/********************************************************************
* Function:     isValidDiskQuota
*
* Description:  Returns true if size is a number between 1 and 1000.
*
* Parameters:   size is a number that indicates what disk quota the 
*               user would like.
*/
function isValidDiskQuota(size)
{
  return ( size >= 1 && size <= 25600 );
}

/********************************************************************
* Function:     isValidMacAddress
*
* Description:  Returns true if mac_address is a valid mac address.
*
* Parameters:   mac_address is a string containing the mac address to
*               validate.
*/
function isValidMacAddress(mac_address)
{
  var theRegEx = /^[0-9A-Fa-f]{2}([\-:.]?)[0-9A-Fa-f]{2}\1[0-9A-Fa-f]{2}\1[0-9A-Fa-f]{2}\1[0-9A-Fa-f]{2}\1[0-9A-Fa-f]{2}$/;
  
  return ( theRegEx.test(mac_address) && mac_address.substring(0,3) != "44");
}

/********************************************************************
* Function:     isValidGroupSize
*
* Description:  Returns true if num_students is a number between 2 and
*               10.
*
* Parameters:   num_students is a string to validate as a number of 
*               students in a project group.
*********************************************************************
*********************************************************************
*********************************************************************
*********************************************************************
* Edited by:    Cody Lanier
*
* Purpose:      Minumum is 1 instead of 2 now
*********************************************************************
*********************************************************************
*********************************************************************
********************************************************************/
function isValidGroupSize(num_students)
{
  var theRegEx = /[\d]{2}/;
  
  return (num_students >= 1 && num_students <= 10); 
}

/********************************************************************
* Function:     isValidClassSize
*
* Description:  Returns true if num_students is a number between
*               1 and 999
*
* Parameters:   num_students is a string to validate as a number of
*               students in a class
*/
function isValidClassSize( num_students )
{
  var theRegEx = /[\d]{1,3}/;
  
  return ( theRegEx.test( num_students ) && 
           num_students >= 1 &&
	   num_students <= 999);
}

/********************************************************************
* Function:     isValidCourse
*
* Description:  Returns true if theCourse is 1-25 chars that are one
*               the following: a-z,A-Z,0-9,_ or a space.
*
* Parameters:   theCourse is string representing a course name
*               (i.e. csc 15)
*/
function isValidCourse( theCourse )
{
  var theRegEx = /^[\w\s]{1,25}$/;
  return theRegEx.test( theCourse );
}

/********************************************************************
* Function:     isValidCourseSection
*
* Description:  Returns true if theSectionNumber is a 1 or 2 digit 
*               number.
*
* Parameters:   theSectionNumber is a number to be validated. 
*/
function isValidCourseSection( theSectionNumber )
{
  var theRegEx = /^[\d]{1,2}$/;
  return theRegEx.test( theSectionNumber );
}

/********************************************************************
* Function:     isValidComments
*
* Description:  Returns true if field has a max of 500 chars.
* 
* Parameters:   theComments is a string.
*/
function isValidComments( theComments )
{
  var theRegEx = /^[\d\D]{0,500}$/;
  return theRegEx.test( theComments );
}

/********************************************************************************
* Function:    validateNewFacultyAccountForm
*
* Description: checks all the fields on this form, and 
*              builds up an error message as it goes.
*        
*              if errors occur, display an appropriate message
*              and set focus to the first field that needs to
*              be checked by the user.
*          
*              otherwise, submit the form.
*       
*/
function validateNewFacultyAccountForm(theForm)
{
  var isValid = true;
  var firstEmptyField = "";
  var errorMessage = topErrorMessage;

  if (! isBlank(theForm.subject.value) )
  {
    
    isValid = false;
    if (theForm.fname.value == "")
      errorMessage += "-Subject: This field was left blank.\n\n";
    
    firstEmptyField = theForm.fname;
  }
  
	if(!isValid)
	{
    alert(errorMessage);

    if(firstEmptyField != "")
      firstEmptyField.focus();
    
  }

  return isValid;
}



/********************************************************************************
* Function:    validateNewStaffAccountForm
*
* Description: checks all the fields on this form, and 
*              builds up an error message as it goes.
*        
*              if errors occur, display an appropriate message
*              and set focus to the first field that needs to
*              be checked by the user.
*          
*              otherwise, submit the form.
*       
*/
function validateNewStaffAccountForm(theForm)
{
  var isValid = true;
  var firstEmptyField = "";
  
  var errorMessage = topErrorMessage;

  if (! isValidName(theForm.fname.value) )
  {
    
    isValid = false;
    if (theForm.fname.value == "")
      errorMessage += "-First Name: This field was left blank.\n\n";
    else
      errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";
    
    firstEmptyField = theForm.fname;
  }
  
  if (! isValidName(theForm.lname.value) )
  {
    isValid = false;

    if (theForm.lname.value == "")
      errorMessage += "-Last Name: This field was left blank.\n\n";
    else 
      errorMessage += "-Last Name: Only letters, spaces, hyphens(-) and apostrophes are accepted.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.lname;
  }
    

  if (! isValidPhone(theForm.phone.value) )
  {
    isValid = false;
    errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.phone;
  }
  
  if (! isValidEmail(theForm.reply_email.value) )
  {
    isValid = false;
    errorMessage += "-Email: Needs to be a valid email.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.reply_email;
  }

  if (! isValidDepartment(theForm.department) )
  {
    isValid = false;
    errorMessage += "-Department: Choose a department from the drop-down list.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.department;
  }
  
  if (! isValidShell(theForm) )
  {
    isValid = false;
    errorMessage += "-Unix Shell: Please choose a Unix shell type.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.shell
  }

  if(! isValidComments( theForm.comments.value ) )
  {
    isValid = false;
    errorMessage += "-Comments: This field accepts a maximum of 500 characters.";

    if( firstEmptyField == "")
      firstEmptyField = theForm.comments;
  }

  if(!isValid)
  {
    alert(errorMessage);

    if(firstEmptyField != "")
      firstEmptyField.focus()
  }
  
  return isValid;
}

/********************************************************************************
* Function:    validateNewStudentAccountForm
* 
* Description: checks all the fields on this form, and 
*              builds up an error message as it goes.
*        
*              if errors occur, display an appropriate message
*              and set focus to the first field that needs to
*              be checked by the user.
*          
*              otherwise, submit the form.
*        
*/
function validateNewStudentAccountForm(theForm)
{
  var isValid = true;
  var firstEmptyField = "";
  
  var errorMessage = topErrorMessage;

  if (! isValidName(theForm.fname.value) )
  {
    
    isValid = false;
    if (theForm.fname.value == "")
      errorMessage += "-First Name: This field was left blank.\n\n";
    else
      errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";
    
    firstEmptyField = theForm.fname;
  }
  
  if (! isValidName(theForm.lname.value) )
  {
    isValid = false;

    if (theForm.lname.value == "")
      errorMessage += "-Last Name: This field was left blank.\n\n";
    else 
      errorMessage += "-Last Name: Only letters, spaces, hyphens(-) and apostrophes are accepted.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.lname;
  }
    

  if (! isValidPhone(theForm.phone.value) )
  {
    isValid = false;
    errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.phone;
  }
  
  if (! isValidEmail(theForm.reply_email.value) )
  {
    isValid = false;
    errorMessage += "-Email: Needs to be a valid email.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.reply_email;
  }

  if (! isValidMajor(theForm.major) )
  {
	if ( num_students.value == 1)
	{
      isValid = false;
	  errorMessage += "-Major: Please specify your major.\n\n";
	    
      if (firstEmptyField == "")
        {firstEmptyField = theForm.major;}
	}
  }
    
  if (! isValidShell(theForm) )
  {
    isValid = false;
    errorMessage += "-Unix Shell: Please choose a Unix shell type.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.shell
  }
  
  if(! isValidComments( theForm.comments.value ) )
  {
    isValid = false;
    errorMessage += "-Comments: This field accepts a maximum of 500 characters.";

    if( firstEmptyField == "")
      firstEmptyField = theForm.comments;
  }

  if(!isValid)
  {
    alert(errorMessage);

    if(firstEmptyField != "")
    {
      firstEmptyField.focus();
    }
  }

  return isValid;
}


/********************************************************************************
* function:    validateLostPasswordForm
* 
* description: checks all the fields on this form, and 
*              builds up an error message as it goes.
*       
*              if errors occur, display an appropriate message
*              and set focus to the first field that needs to
*              be checked by the user.
*         
*              otherwise, generate a printable reciept.
*       
*/
function validateLostPasswordForm(theForm)
{
  var isValid = true;
  var firstEmptyField = "";
  
  var errorMessage = topErrorMessage;
  
  if (! isValidName(theForm.fname.value) )
  {
    
    isValid = false;
    if (theForm.fname.value == "")
      errorMessage += "-First Name: This field was left blank.\n\n";
    else
      errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";
    
    firstEmptyField = theForm.fname;
  }
  
  if (! isValidName(theForm.lname.value) )
  {
    isValid = false;

    if (theForm.lname.value == "")
      errorMessage += "-Last Name: This field was left blank.\n\n";
    else 
      errorMessage += "-Last Name: Only letters, spaces, hyphens(-) and apostrophes are accepted.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.lname;
  }
  
  if (! isValidPhone(theForm.phone.value) )
  {
    isValid = false;
    errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.phone;
  }
  
  if (! isValidEmail(theForm.reply_email.value) )
  {
    isValid = false;
    errorMessage += "-Email: Needs to be a valid email.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.reply_email;
  }
  
  if (! isValidAccountName(theForm.account_name.value) )
  {
    isValid = false;
    errorMessage += "-Account Name: Please enter a valid account name (3 - 8 letters, numbers, and underscores).";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }
  
  if(!isValid)
  {
    alert(errorMessage);

    if(firstEmptyField)
      firstEmptyField.focus();
  }

  return isValid;
}



/********************************************************************************
* Function:    validateDiskQuotaForm
* 
* Description: checks all the fields on this form, and 
*              builds up an error message as it goes.
*       
*              if errors occur, display an appropriate message
*              and set focus to the first field that needs to
*              be checked by the user.
*         
*              otherwise, generate a printable reciept.
*       
*/
function validateDiskQuotaForm(theForm)
{
  var isValid = true;
  var firstEmptyField = "";
  var errorMessage = topErrorMessage;
  
  if (! isValidName(theForm.fname.value) )
  {
    
    isValid = false;
    if (theForm.fname.value == "")
      errorMessage += "-First Name: This field was left blank.\n\n";
    else
      errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";
    
    firstEmptyField = theForm.fname;
  }
  
  if (! isValidName(theForm.lname.value) )
  {
    isValid = false;

    if (theForm.lname.value == "")
      errorMessage += "-Last Name: This field was left blank.\n\n";
    else 
      errorMessage += "-Last Name: Only letters, spaces, hyphens(-) and apostrophes are accepted.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.lname;
  }
  
  if (! isValidPhone(theForm.phone.value) )
  {
    isValid = false;
    errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.phone;
  }
  
  if (! isValidEmail(theForm.reply_email.value) )
  {
    isValid = false;
    errorMessage += "-Email: Needs to be a valid email.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.reply_email;
  }
  
  if (! isValidDiskQuota(theForm.disk_quota.value) )
  {
    isValid = false;
    errorMessage += "-Disk Quota: Please enter a value between 1 and 25600\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }
  
  if (! isValidAccountName(theForm.account_name.value) )
  {
    isValid = false;
    errorMessage += "-Account Name: Please enter a valid account name (3 - 8 letters, numbers, and underscores).\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }
  
  var theRegEx = /[\w]+/;
  
  if (! theRegEx.test(theForm.disk_quota_reason.value) )
  {
    isValid = false;
    errorMessage += "-Reason For Disk Quota Change: You must specify a reason.";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.disk_quota_reason;
  }

  if(!isValid)
  {
    alert(errorMessage);

    if(firstEmptyField != "")
      firstEmptyField.focus();
  }
  
  return isValid;
}


/********************************************************************************
* function:     validateIPForm
* 
* description:  checks all the fields on this form, and 
*               builds up an error message as it goes.
*       
*               if errors occur, display an appropriate message
*               and set focus to the first field that needs to
*               be checked by the user.
*         
*               otherwise, submit the form.
*       
*/
function validateIPForm(theForm)
{
  var isValid = true;
  var firstEmptyField = "";
  var errorMessage = topErrorMessage;

  if (! isValidName(theForm.fname.value) )
  {
    
    isValid = false;
    if (theForm.fname.value == "")
      errorMessage += "-First Name: This field was left blank.\n\n";
    else
      errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";
    
    firstEmptyField = theForm.fname;
  }
  
  if (! isValidName(theForm.lname.value) )
  {
    isValid = false;

    if (theForm.lname.value == "")
      errorMessage += "-Last Name: This field was left blank.\n\n";
    else 
      errorMessage += "-Last Name: Only letters, spaces, hyphens(-) and apostrophes are accepted.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.lname;
  }
    

  if (! isValidPhone(theForm.phone.value) )
  {
    isValid = false;
    errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.phone;
  }
  
  if (! isValidEmail(theForm.reply_email.value) )
  {
    isValid = false;
    errorMessage += "-Email: Needs to be a valid email.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.reply_email;
  }

  if (! isValidDepartment(theForm.department) )
  {
    isValid = false;
    errorMessage += "-Department: Choose a department from the drop-down list.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.department;
  }
  
  if (! isValidAccountName(theForm.account_name.value) )
  {
    isValid = false;
    errorMessage += "-Account Name: Please enter a valid account name (3 - 8 letters, numbers, and underscores).\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }
  
  if (! isValidMacAddress(theForm.mac_address.value) )
  {
    isValid = false;
    errorMessage += "-MAC Address: Please enter a valid MAC Address.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.mac_address;
  }
  else if(theForm.mac_address.value.substring(0,2) == "44")
  {
    isValid = false;
    errorMessage += "-MAC Address: Please do not use 44 as the first two digits in your MAC Address - this specifies the router, and is not the number you are looking for. If you need help finding your mac address, please click the \"need help?\" link.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.mac_address;
  }
  
  var theRegEx = /^\s*$/;
  if ( theForm.device_type.selectedIndex == 0 )
  {
    isValid = false;
    errorMessage += "-Device Type: Please select a device type.";
    
    if(firstEmptyField == "")
      firstEmptyField = theForm.device_type;
  }
  else if (theForm.device_type.selectedIndex == 3 && theRegEx.test(theForm.device_othertype.value))
  {
    isValid = false;
    errorMessage += "-Device Type: When selecting \"Other\" you mush fill out the text field labeled \"Specify Other\").";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.device_othertype;
  }

  if (isValid)
  {
    if(! confirm("*********************************************************************\n" +        
                "* TO ALL STUDENTS\n" + 
                "*********************************************************************\n" +
                "\nThe ECS network connections are tied to" + 
                " students' Gaia accounts.  If the account goes inactive and" + 
                " expires, then the network connection will also expire.\n\n" + 
                "NOTE: If you have previously submitted invalid information," +
                " please resubmit your information and make a note in the " +
                "\"Comments\" field.\n\n" + 
                "If you have read and understand all the information above, please " +
                "click \"ok\" to send your request."))            
    {
      isValid = false;
    }
  }
  else
  {
    alert(errorMessage);
    
    if (firstEmptyField != "")
      firstEmptyField.focus();
  }

  return isValid;
}



/********************************************************************************
* function:     validateStudentProjectAccountForm1
* 
* description:  checks all the fields on the first student account form, and 
*               builds up an error message as it goes.
*       
*               if errors occur, display an appropriate message
*               and set focus to the first field that needs to
*               be checked by the user.
*         
*               otherwise, submit the form.
*
*********************************************************************
* Edited by:    Cody Lanier
*
* Purpose:      removed some code, but made similar validate functions for each form
*/

function validateStudentProjectAccountForm1(theForm)
{
  var firstEmptyField = "";
  var errorMessage = "";
  var isValid = true;

  errorMessage = "Please check the following fields for missing information:\n\n";
  if (! isValidAccountName(theForm.account_name.value) )
  {
    isValid = false;
	errorMessage += "-Account Name: Please enter a valid account name (3 - 8 letters only).\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }

  if (! isValidDiskQuota(theForm.disk_quota.value) )
  {
    isValid = false;
    errorMessage += "-Disk Quota: Please enter a value between 1 and 1000\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }

  if(! isValidName(theForm.project_advisor.value) )
  {
    isValid = false;
    
    if (theForm.project_advisor.value == "")
      errorMessage += "-Project Advisor: This field was left blank.\n\n";
    else
      errorMessage += "-Project Advisor: Only letters, spaces, periods and hyphens(-) are accepted.\n\n";

    if( firstEmptyField == "" )
      firstEmptyField = theForm.project_advisor;
  }
    
  if (! isValidShell(theForm) )
  {
    isValid = false;
    errorMessage += "-Unix Shell: Please choose a Unix shell type.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.shell
  }
  
  if (! isValidGroupSize(theForm.num_students.value) )
  {
    isValid = false;
    errorMessage += "-Number of Participants: Please enter the number of students (1-10) that will be using this account.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.num_students;
  }
  
  if (! isValidMajor(theForm.major) )
  {
	  isValid = false;
	  errorMessage += "-Major: Please specify your major.\n\n";
	    
      if (firstEmptyField == "")
        {firstEmptyField = theForm.major;}
  }
  
  if( theForm.num_semesters.selectedIndex == 0 )
  {
    isValid = false;
    errorMessage += "-How long will the account be active: Please choose the amount of time that the account will be active.\n\n";
    
    if( firstEmptyField == "" )
      firstEmptyField = theForm.num_semesters;
  }
  else if( theForm.num_semesters.selectedIndex == 3 )
  { // "Other" was selected, so check the text field
    var numSemesters = parseInt( theForm.num_semesters_other.value );   
    
    if( numSemesters == 0 || isNaN( numSemesters ) )
    { // couldn't get the number of semesters that they typed
    
      isValid = false;
      errorMessage += "-How long will the account be active: Please enter the number of semesters that the account should be active.\n\n";
      
      if( firstEmptyField == "" )
        firstEmptyField = theForm.num_semesters_other;
    }
  }  
  
  if(! isValidComments( theForm.comments.value ) )
  {
    isValid = false;
    errorMessage += "-Comments: This field accepts a maximum of 500 characters.";

    if( firstEmptyField == "")
      firstEmptyField = theForm.comments;
  }
 
  if(!isValid)
  {
    alert(errorMessage);

    if(firstEmptyField != "")
      firstEmptyField.focus();
  }
  
  return isValid;
}

/********************************************************************************
* function:     validateStudentMySQLDatabaseForm1
* 
* description:  checks all the fields on the first student account form, and 
*               builds up an error message as it goes.
*       
*               if errors occur, display an appropriate message
*               and set focus to the first field that needs to
*               be checked by the user.
*         
*               otherwise, submit the form.
*
*********************************************************************
* Edited by:    Cody Lanier
*
* Purpose:      validates specific to MySQLacct.php
*/

function validateStudentMySQLDatabaseForm1(theForm)
{
  var firstEmptyField = "";
  var errorMessage = "";
  var isValid = true;

  errorMessage = "Please check the following fields for missing information:\n\n";
  
  if (! isValidAccountName(theForm.database_name.value) )
  {
    isValid = false;
	errorMessage += "-Database Name: Please enter a valid database name (3 - 8 letters only).\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.database_name;
  }
  
  if(! isValidName(theForm.project_advisor.value) )
  {
    isValid = false;
    
    if (theForm.project_advisor.value == "")
      errorMessage += "-Project Advisor: This field was left blank.\n\n";
    else
      errorMessage += "-Project Advisor: Only letters, spaces, periods and hyphens(-) are accepted.\n\n";

    if( firstEmptyField == "" )
      firstEmptyField = theForm.project_advisor;
  }
  
  if (! isValidGroupSize(theForm.num_students.value) )
  {
    isValid = false;
    errorMessage += "-Number of Participants: Please enter the number of students (1-10) that will be using this account.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.num_students;
  }
  
  if (! isValidMajor(theForm.major) )
  {
      isValid = false;
	  errorMessage += "-Major: Please specify your major.\n\n";
	    
      if (firstEmptyField == "")
        {firstEmptyField = theForm.major;}
  }
  
  if( theForm.num_semesters.selectedIndex == 0 )
  {
    isValid = false;
    errorMessage += "-How long will the account be active: Please choose the amount of time that the account will be active.\n\n";
    
    if( firstEmptyField == "" )
      firstEmptyField = theForm.num_semesters;
  }
  else if( theForm.num_semesters.selectedIndex == 3 )
  { // "Other" was selected, so check the text field
    var numSemesters = parseInt( theForm.num_semesters_other.value );   
    
    if( numSemesters == 0 || isNaN( numSemesters ) )
    { // couldn't get the number of semesters that they typed
    
      isValid = false;
      errorMessage += "-How long will the account be active: Please enter the number of semesters that the account should be active.\n\n";
      
      if( firstEmptyField == "" )
        firstEmptyField = theForm.num_semesters_other;
    }
  }  
  
  var itemChecked = false;
  var i = 0;

    // Find out how many items are checked in the permissions checkbox 
    // group.
  for (i = 0; i < theForm.elements.length; i++) {
      theField = theForm.elements[i];
		
		/***********
		**added []**
		***********/
      if (theField.name == 'mysql_permissions[]') {
          if (theField.type == 'checkbox' && theField.checked) {
              itemChecked = true;
              break;
          }
      }
  }

  if (!itemChecked) {
      isValid = false;
      errorMessage += "-Your Permissions: Please check at least one item.\n\n";
  }

  if(! isValidComments( theForm.comments.value ) )
  {
    isValid = false;
    errorMessage += "-Comments: This field accepts a maximum of 500 characters.";

    if( firstEmptyField == "")
      firstEmptyField = theForm.comments;
  }
  
  if(!isValid)
  {
    alert(errorMessage);

    if(firstEmptyField != "")
      firstEmptyField.focus();
  }
  
  return isValid;
}

/********************************************************************************
* function:     validateStudentDualAccountForm1
* 
* description:  checks all the fields on the first student account form, and 
*               builds up an error message as it goes.
*       
*               if errors occur, display an appropriate message
*               and set focus to the first field that needs to
*               be checked by the user.
*         
*               otherwise, submit the form.
*
*********************************************************************
* Edited by:    Cody Lanier
*
* Purpose:      validates specific to DualForm.php
*/

function validateStudentDualAccountForm1(theForm)
{
  var firstEmptyField = "";
  var errorMessage = "";
  var isValid = true;

  errorMessage = "Please check the following fields for missing information:\n\n";
  if (! isValidAccountName(theForm.account_name.value) )
  {
    isValid = false;
	errorMessage += "-Account/Database Name: Please enter a valid account/database name (3 - 8 letters only).\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }

  if (! isValidDiskQuota(theForm.disk_quota.value) )
  {
    isValid = false;
    errorMessage += "-Disk Quota: Please enter a value between 1 and 1000\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.account_name;
  }

  if(! isValidName(theForm.project_advisor.value) )
  {
    isValid = false;
    
    if (theForm.project_advisor.value == "")
      errorMessage += "-Project Advisor: This field was left blank.\n\n";
    else
      errorMessage += "-Project Advisor: Only letters, spaces, periods and hyphens(-) are accepted.\n\n";

    if( firstEmptyField == "" )
      firstEmptyField = theForm.project_advisor;
  }
    
  if (! isValidShell(theForm) )
  {
    isValid = false;
    errorMessage += "-Unix Shell: Please choose a Unix shell type.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.shell
  }
  
  if (! isValidGroupSize(theForm.num_students.value) )
  {
    isValid = false;
    errorMessage += "-Number of Students: Please enter the number of students (1-10) that will be using this account.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.num_students;
  }
  
  if (! isValidMajor(theForm.major) )
  {
	  isValid = false;
	  errorMessage += "-Major: Please specify your major.\n\n";
	    
      if (firstEmptyField == "")
        {firstEmptyField = theForm.major;}
  }
  
  if( theForm.num_semesters.selectedIndex == 0 )
  {
    isValid = false;
    errorMessage += "-How long will the account be active: Please choose the amount of time that the account will be active.\n\n";
    
    if( firstEmptyField == "" )
      firstEmptyField = theForm.num_semesters;
  }
  else if( theForm.num_semesters.selectedIndex == 3 )
  { // "Other" was selected, so check the text field
    var numSemesters = parseInt( theForm.num_semesters_other.value );   
    
    if( numSemesters == 0 || isNaN( numSemesters ) )
    { // couldn't get the number of semesters that they typed
    
      isValid = false;
      errorMessage += "-How long will the account be active: Please enter the number of semesters that the account should be active.\n\n";
      
      if( firstEmptyField == "" )
        firstEmptyField = theForm.num_semesters_other;
    }
  }  
    
    // Find out how many items are checked in the permissions checkbox 
    // group.
	var itemChecked = false;
	var i = 0;
	
	for (i = 0; i < theForm.elements.length; i++) {
      theField = theForm.elements[i];
		
		/***********
		**added []**
		***********/
      if (theField.name == 'mysql_permissions[]') {
          if (theField.type == 'checkbox' && theField.checked) {
              itemChecked = true;
              break;
          }
      }
	}
  
	if (!itemChecked) {
      isValid = false;
      errorMessage += "-Your Permissions: Please check at least one item.\n\n";
	}

	if(! isValidComments( theForm.comments.value ) )
	{
		isValid = false;
		errorMessage += "-Comments: This field accepts a maximum of 500 characters.";

		if( firstEmptyField == "")
		firstEmptyField = theForm.comments;
	}
  
	if(!isValid)
	{
		alert(errorMessage);

		if(firstEmptyField != "")
		firstEmptyField.focus();
	}
  
	return isValid;
}

/********************************************************************************
* function:     validateStudentProjectAccountForm2
* 
* description:  checks all the fields on this form, and 
*               builds up an error message as it goes.
*       
*               if errors occur, display an appropriate message
*               and set focus to the first field that needs to
*               be checked by the user.
*         
*               otherwise, submit the form.
*/
function validateStudentProjectAccountForm2(theForm)
{
  var firstEmptyField = "";
  var errorMessage = "";
  var isValid = true;
  var studentNum = 1; // the student currently being processed in the loop.
  
  var i = 0;
  var fnameField, lnameField, phoneField, emailField, majorField;
  var numStudents = document.getElementById("num_students");
  
  // Find the last field with student info in it.
  while (theForm.elements[i].name != "endOfStudents") i++;
  var lastStudentFieldPosition = i - 1;
  
  i = 0;
  while(theForm.elements[i].name != "fname1") i++; // find the first name field
  
  while (isValid && i <= lastStudentFieldPosition)
  {
    // Make sure every student filled in fields correctly:
    fnameField = theForm.elements[i];
    lnameField = theForm.elements[i + 1];
    phoneField = theForm.elements[i + 2];
    emailField = theForm.elements[i + 3];
	/***********************************************************
	****accounts for the major field, if more than 1 student****
	***********************************************************/
	if (numStudents.value > 1)
	{
		majorField = theForm.elements[i + 4];
	}
	
    if (! isValidName(fnameField.value) )
    {
      isValid = false;
      
      if (fnameField.value == "")
        errorMessage += "-First Name: This field was left blank.\n\n";
      else
        errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";
      
      if (firstEmptyField == "")
        firstEmptyField = fnameField;
    }
    
    if (! isValidName(lnameField.value) )
    {
      isValid = false;
  
      if (lnameField.value == "")
        errorMessage += "-Last Name: This field was left blank.\n\n";
      else 
        errorMessage += "-Last Name: Only letters, spaces, hyphens(-) and apostrophes are accepted.\n\n";
    
      if (firstEmptyField == "")
        firstEmptyField = lnameField;
    }
    
    if (! isValidPhone(phoneField.value) )
    {
      isValid = false;
      errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";
    
      if (firstEmptyField == "")
        firstEmptyField = phoneField;
    }
    
    if (! isValidEmail(emailField.value) )
    {
      isValid = false;
      errorMessage += "-Email: Needs to be a valid email.\n\n";
      
      if (firstEmptyField == "")
        firstEmptyField = emailField;
    }
	/*********************************************
	****validate major, if more than 2 students***
	*********************************************/
	if (numStudents.value > 1)
	{
		if (! isValidMajor(majorField))
		{
			isValid = false;
			errorMessage += "-Major: Please choose a major.\n\n";
			
			if (firstEmptyField == "")
				firstEmptyField = majorField;
		}
	}
    
    if (!isValid)
    {  
      errorMessage = "Please check the following fields for STUDENT #" + studentNum + ": \n\n" + errorMessage;
      
      if (firstEmptyField != "")
        firstEmptyField.focus();
    }
    
	if (numStudents.value > 1)
	{
		i += 5; // Number of fields for each student.
	}
	else
	{
		i += 4; // Number of fields for each student.
    }
	studentNum++;
  } // end while
 
  if(!isValid)
  {
    alert(errorMessage);

    if(firstEmptyField != "")
      firstEmptyField.focus();
  }
  
  return isValid;
}

/********************************************************************
* Function:    validateNewlassAccountForm 
*
* Description: Checks all the fields in theForm, accumulating 
*              accumulating an error message as it goes.
*        
*              Returns true if all fields are okay, otherwise,
*              it returns false.
*
* Parameters:  Must be the newClassAccountForm from useraccount.cgi 
*/
function validateNewClassAccountForm( theForm )
{
  var firstEmptyField = "";
  var errorMessage = topErrorMessage;
  var isValid = true;
  var theRegEx; // a regular expression object
  
  if (! isValidName(theForm.fname.value) )
  {
    
    isValid = false;
    if (theForm.fname.value == "")
      errorMessage += "-First Name: This field was left blank.\n\n";
    else
      errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";
    
    firstEmptyField = theForm.fname;
  }
  
  if (! isValidName(theForm.lname.value) )
  {
    isValid = false;

    if (theForm.lname.value == "")
      errorMessage += "-Last Name: This field was left blank.\n\n";
    else 
      errorMessage += "-Last Name: Only letters, spaces, hyphens(-) and apostrophes are accepted.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.lname;
  }
    
  if (! isValidPhone(theForm.phone.value) )
  {
    isValid = false;
    errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";
  
    if (firstEmptyField == "")
      firstEmptyField = theForm.phone;
  }
  
  if (! isValidEmail(theForm.reply_email.value) )
  {
    isValid = false;
    errorMessage += "-Email: Needs to be a valid email.\n\n";
    
    if (firstEmptyField == "")
      firstEmptyField = theForm.reply_email;
  }
  
  if(! isValidAccountName( theForm.account_name.value ) )
  {
    isValid = false;
    errorMessage += "-Account Name: Please enter a valid account name (3 - 8 letters, numbers, and underscores).\n\n";

    if( firstEmptyField == "" )
      firstEmptyField = theForm.account_name;
  }
  
  if(! isValidCourse( theForm.course.value ) )
  {
    isValid = false;
    errorMessage += "-Course: Please enter a valid course name (i.e. \"CSC 15\" -- only letters, numbers, and spaces).\n\n";

    if( firstEmptyField == "" )
      firstEmptyField = theForm.course;
  }
  
  if(! isValidCourseSection( theForm.section_number.value ) )
  {
    isValid = false;
    errorMessage += "-Section Number: Please enter a section number for the course (i.e. 12).\n\n";

    if( firstEmptyField == "")
      firstEmptyField = theForm.section_number;
  }

  theRegEx = /[\d]{1,3}/;
  
  if(! isValidClassSize( theForm.num_students.value ) )
  {
    isValid = false;
    errorMessage += "-Number of Students: Please enter the number of students in your class.\n\n";

    if( firstEmptyField == "" )
      firstEmptyField = theForm.num_students;
  }


  if(! isValidShell( theForm ) )
  {
    isValid = false;
    errorMessage += "-Unix Shell: Please choose a Unix shell type.\n\n";

    if( firstEmptyField == "" )
      firstEmptyField = theForm.shell;
  }

  if(! isValidDiskQuota( theForm.disk_quota.value ) )
  {
    isValid = false;
    errorMessage += "-Disk Quota: Please enter a value between 1 and 1000.\n\n";
    
    if( firstEmptyField == "" )
      firstEmptyField = theForm.disk_quota;
  }

	if( theForm.num_semesters.selectedIndex == 0 )
  {
    isValid = false;
    errorMessage += "-How long will the account be active: Please choose the amount of time that the account will be active.\n\n";
    
    if( firstEmptyField == "" )
      firstEmptyField = theForm.num_semesters;
  }
  else if( theForm.num_semesters.selectedIndex == 3 )
  { // "Other" was selected, so check the text field
    var numSemesters = parseInt( theForm.num_semesters_other.value );   
    
    if( numSemesters == 0 || isNaN( numSemesters ) )
    { // couldn't get the number of semesters that they typed
    
      isValid = false;
      errorMessage += "-How long will the account be active: Please enter the number of semesters that the account should be active.\n\n";
      
      if( firstEmptyField == "" )
        firstEmptyField = theForm.num_semesters_other;
    }
  }

  if(! isValidComments( theForm.comments.value ) )
  {
    isValid = false;
    errorMessage += "-Comments: This field accepts a maximum of 500 characters.";

    if( firstEmptyField == "")
      firstEmptyField = theForm.comments;
  }
  
  if(! isValid )
  {
    alert(errorMessage);

    if(firstEmptyField != "")
      firstEmptyField.focus();
  }

  return isValid;
}





/********************************************************************
* Function:    validateMySQLRequest2
*
* Description: Validates the 2nd mysql form.
*
* Parameters:  theForm is the form object to validate 
*              (mysqlRequestForm2)
*/
function validateMySQLRequest2( theForm )
{
  var isValid = true;
  var firstEmptyField = "";
  var str = "";
  var firstFieldPos = -1; // position of the first contact information field.
  var lastFieldPos = -1; // position of the last contact information field.
  var numParticipants = theForm.num_participants.value;
  var currParticipantNum = 1;
  var errorMessage = "";
  
  // Find the position of the first "first name" field.
  for( i = 0; i < theForm.elements.length; i++ )
  {
    if( theForm.elements[i].name == "fname1" )
    {
      firstFieldPos = i;
      break;
    }
  }
  
  // Find position of the last "reply_email" field.
  for( i = 0; i < theForm.elements.length; i++ )
  {
  	if( ( theForm.elements[i].name ).indexOf( "reply_email" ) == 0 )
	{
		lastFieldPos = i;
	}
  }
  
  // Debug the start and end field:
  //alert( theForm.elements[firstFieldPos].name );
  //alert( theForm.elements[lastFieldPos].name );
  
  i = firstFieldPos;
  var numStudents = document.getElementById("num_students");
  
  // Check input for all participants
  while (isValid && i <= lastFieldPos)
  {
    fnameField       = theForm.elements[i];
    lnameField       = theForm.elements[i + 1];
    phoneField       = theForm.elements[i + 2];
    emailField       = theForm.elements[i + 3];
	if (numStudents.value > 1)
	{
		majorField = theForm.elements[i + 4];
	}
	
    if( ! isValidName( fnameField.value ) )
    {
      isValid = false;
      errorMessage += "-First Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";

      if( firstEmptyField == "" )
	firstEmptyField = fnameField;
    }

    if( ! isValidName( lnameField.value ) ) 
    {
      isValid = false;
      errorMessage += "-Last Name: Only letters, spaces, hyphens(-), and apostrophes are accepted.\n\n";

      if( firstEmptyField == "" )
	firstEmptyField = lnameField;
    }

    if( ! isValidPhone( phoneField.value ) ) 
    {
      isValid = false;
      errorMessage += "-Phone: Try the following format: (916) 767-7777.\n\n";

      if( firstEmptyField == "" )
	firstEmptyField = phoneField;
    }

    if( ! isValidEmail( emailField.value ) )
    {
		isValid = false;
		errorMessage += "-Email: Please enter a valid email.\n\n";

	if( firstEmptyField == "" )
		firstEmptyField = emailField;
    }
	
	if (numStudents.value > 1)
	{
		if( ! isValidMajor( majorField ) )
		{
			isValid = false;
			errorMessage += "-Major: Please enter a major.\n\n";

			if( firstEmptyField == "" )
				firstEmptyField = majorField;
		}
	}

    if( ! isValid )
    {
      errorMessage = "Please check the information entered for participant #" 
		     + currParticipantNum + ":\n\n" + errorMessage;
    }

	if (numStudents > 1)
	{
		i += 5
	}
	else
	{
		i += 4;
	}
    currParticipantNum++;
  }

  if( ! isValid )
  {
    alert( errorMessage );
    
    if( firstEmptyField != "" )
      firstEmptyField.focus();
  }

  return isValid;
}
