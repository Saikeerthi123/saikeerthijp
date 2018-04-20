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
			if($cnt==0)
			{
				echo date('Y-m-d H:i',strtotime('-0 minutes',strtotime($slot)));
			}
			else{
				$cnt=1;
				while($cnt!=0)
				{
					$quer="select * from schedule where timeslot='$slot'";
					$ru=mysqli_query($conn,$quer);
					$cnt = mysqli_num_rows($ru);
					echo "-----------------";
					echo $cnt;
					echo "-----------------------";
					$slot=date('Y-m-d H:i',strtotime('+15 minutes',strtotime($slot)));
				}
				echo date('Y-m-d H:i',strtotime('-15 minutes',strtotime($slot)));
			}
		}
		else
		{
			$query="select * from schedule WHERE TIMESTAMPDIFF(minute,timeslot,'$slot')<15";
			$run=mysqli_query($conn,$query);
			$cnt = mysqli_num_rows($run);
			if($cnt==0)
			{
				echo date('Y-m-d H:i',strtotime('-0 minutes',strtotime($slot)));
			}
			else
			{
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
				
				$date1 = strtotime($slot);
				$minu1=date('i', $date);
				
				$date = strtotime($encode[1]);
				$minu2=date('i', $date);
				
				echo $minu2-$minu1;
				
				if($encode[1]>$encode[0] && $minu2-$minu1<15)
				{
					$en=$encode[1];
				}
				else
				{
					$en=$encode[0];
				}
				echo "_______________________";
				echo $en;
				echo "____________________________";
				$cn=1;
				while($cn!=0)
				{
					$quer="select * from schedule where timeslot='$en'";
					$ru=mysqli_query($conn,$quer);
					$cn = mysqli_num_rows($ru);
					$en=date('Y-m-d H:i',strtotime('+15 minutes',strtotime($en)));
				}
				echo date('Y-m-d H:i',strtotime('-15 minutes',strtotime($en)));
			}
		}
		
}
		



?>