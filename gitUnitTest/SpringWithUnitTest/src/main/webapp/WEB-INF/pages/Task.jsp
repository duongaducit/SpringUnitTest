<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<title>New/Edit Account</title>
</head>
<body>
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4">
			<h1>New/Edit Account</h1>
			<form:form action="saveTask" method="post" modelAttribute="task">
				<div class="form-group">
					<label for="id">Id Task</label>
					<form:input path="idTask" class="form-control" id="id" />
					<font color="red"><form:errors path="idTask" /></font>
				</div>
				<div class="form-group">
					<label for="name">Name Task</label>
					<form:input path="nameTask" class="form-control" id="name" />
					<font color="red"><form:errors path="nameTask" /></font>
				</div>
				<div class="form-group">
					<label for="time">Time Update</label> <label class="form-control"
						id="name" />${task.timeUpdate }
				</div>
				<div class="form-group">
					<label for="sel1">Status Task:</label>
					<form:select path="statusTask" class="form-control" id="sel1">
						<form:option value="Done!">Done!</form:option>
						<form:option value="Doing...">Doing...</form:option>
						<form:option value="Do not.">Do not.</form:option>
					</form:select>
				</div>
				<div class="form-group">
					<label>Public Task:    </label>
					<form:checkbox path="publicTask" />
				</div>
				<input type="submit" class="btn btn-success" value="Save">
			</form:form>
		</div>
		<div class="col-sm-4"></div>
	</div>
</body>
</html>