function clickDelete(){
		var value = confirm("Bạn có muốn xóa không?");
		if (value == true)
			return true;
		else
			return false;
	}
	function saveSQL(){
		var value = confirm("Bạn có muốn lưu tất cả thay đổi không?");
		if (value == true)
			return true;
		else
			return false;
	}
	function clickChange(id){
		//alert(id);
		var id = id;
		var xmlhttp=new XMLHttpRequest();
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200)
							{}
		}

		xmlhttp.open("GET","/SpringWithUnitTest/change?idTask="+id,false);
		xmlhttp.send();
		
	}
	function changeStatus(id,stt){
		var id = id;
		var status = stt.value;
		var xmlhttp=new XMLHttpRequest();
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200)
							{}
		}

		xmlhttp.open("GET","/SpringWithUnitTest/changeSTT?idTask="+id+"&statusTask="+status,false);
		xmlhttp.send();
	}
	function selectRecord(record){
		var recordPage = record.value;
		var xmlhttp=new XMLHttpRequest();
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200)
							{}
		}

		xmlhttp.open("GET","/SpringWithUnitTest/selectRecord?record=" + recordPage,false);
		xmlhttp.send();
		location.reload(true);
	}