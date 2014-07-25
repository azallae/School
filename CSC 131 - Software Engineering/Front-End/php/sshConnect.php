<?php
	set_include_path(get_include_path() . PATH_SEPARATOR . 'phpseclib0');
	include('Net/SSH2.php');
	$ssh = new Net_SSH2('131-server.ecs.csus.edu'); //starting the ssh connection to localhost
	if (!$ssh->login('root', 'have.phun')) { //if you can't log on...
		exit('Login Failed');
	}
	else{
		$output = $ssh->exec('ypcat passwd | grep -i '.$_POST['email'].'');
		
		if($output == ""){
			echo '0';
		}	
		else { echo '1'; }
	}
?>