$(document).ready(function(){
    $('#validation_email').blur(function(){
		var validation_email = $(this).val(); // assigns the id to if the field to a variable
		if(validation_email != ''){ // will not execute with the field is empty
			$.post('php/sshConnect.php', { email:validation_email }, function(data){
				if ( data == '0' )
				{
					document.getElementById("tmp_email").innerHTML = '<span style="color:red">Invalid email!</span>'; // Changes color to red and displays the invalid message
					$("input[type=submit]").attr("disabled", "disabled"); // Disable the submit button
				}
				else
				{
					document.getElementById("tmp_email").innerHTML = '<span style="color:black">@ecs.csus.edu</span>'; 
					$("input[type=submit]").removeAttr("disabled");
				}
			});
		}
		else{
			$('#tmp_email').text('@ecs.csus.edu'); // clears the search status
		}
    });
});