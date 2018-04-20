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
		$cnt=1;
		while($cnt!=0)
		{
			$query="select * from schedule where timeslot='$slot'";
			$run=mysqli_query($conn,$query);
			$cnt = mysqli_num_rows($run);
			$slot=date('Y-m-d H:i',strtotime('+15 minutes',strtotime($slot)));
			
			
		}
		
		echo date('Y-m-d H:i',strtotime('-15 minutes',strtotime($slot)));
}
		



?>