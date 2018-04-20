<?php
$conn=mysqli_connect("localhost","root","");
mysqli_select_db($conn,"jpos");
if($conn)
{
	echo "<script>alert('successfully connected')</script>";
}
else
{
	echo "<script>alert('not connected ')</script>";
}
if(isset($_POST['submit']))
{
		$name=$_POST['user_name'];
		$slot=$_POST['input_time'];
		 
		 echo "minutes are: ";
		 echo "<br>";
		$date = strtotime($slot);
		$minu=date('i', $date);
		echo $minu;
		echo "----------------------";
		echo "<br>";
		
		if($minu%15==0)
		{
			echo "checking minu";
			$que="select * from schedule where timeslot='$slot'";
			$r=mysqli_query($conn,$que);
			$cnt = mysqli_num_rows($r);
			/*if($cnt==0)
			{
				echo date('h:i:s',strtotime($slot));
			}
			else{
				$cn=1;*/
			while($cnt!=0)
			{
				$quer="select * from schedule where timeslot='$slot'";
				$ru=mysqli_query($conn,$quer);
				$cnt = mysqli_num_rows($ru);
				$en=date('h:i:s',strtotime($slot . ' +15 minutes'));
			}
			echo date('h:i:s',strtotime($slot . ' -15 minutes'));
			//}
		}
		else
		{
			$query="select * from schedule WHERE TIMESTAMPDIFF(minute,timeslot,'$slot')<15";
			$run=mysqli_query($conn,$query);
			$encode = array();
			while($row = mysqli_fetch_array($run)){
				$encode[] = $row['timeslot'];	
			}
			$s=sizeof($encode);
			for($x = 0; $x <$s; $x++) {
				echo $encode[$x];
				echo "<br>";
			}
			echo "comparing";
			echo "<br>";
			if($encode[1]>$encode[0])
			{
				echo $encode[1];
			}
			$en=$encode[1];
			$cn=1;
			while($cn!=0)
			{
				$quer="select * from schedule where timeslot='$en'";
				$ru=mysqli_query($conn,$quer);
				$cn = mysqli_num_rows($ru);
				$en=date('h:i:s',strtotime($en . ' +15 minutes'));
			}
			echo date('h:i:s',strtotime($en . ' -15 minutes'));
		}
}
		



?>