$(document).ready(function(){
    $('#database_name').blur(function(){
		var database_name = $(this).val(); // assigns the id to if the field to a variable
		
		if(database_name != ''){ // will not execute with the field is empty
			$.post('php/SQLacctValidate.php', { acc_name:database_name }, function(data){ //sends data to validate (name) and returns content
				if(data == '0'){
					$("input[type=submit]").removeAttr("disabled");
					$('#tmp').text(''); // clears the search status
					
				}else{
					$("input[type=submit]").attr("disabled", "disabled");
					document.getElementById("tmp").innerHTML = '<span style="color:red">Sorry, already exist.</span>'; // changes color to red if username exist
					
				}
			});
		}
		else{
			$('#tmp').text(''); // clears the search status
		}
    });
});