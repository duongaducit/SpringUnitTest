<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/add.js" >
</script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<title>Task Manager Home</title>
</head>
<body>
	<div class="panel-group">
		<div class="panel-footers">
			<label class="title">Task List</label>
		</div>
		<!-- footer 1 -->
		<div class="panel-footer">
		 <div class = "row">
		 	<div class = "col-sm-4"><a href="newTask" class="btn btn-primary btn-sm active" role="button">Add</a></div>
		 	<div class = "col-sm-4"><label class="label-total">record ${currentRecord } in total
				${maxRecord }</label></div>
		 	<div class = "col-sm-4">
		 		<select id = "selectRecord" onchange="selectRecord(this);">
								<option value="3" ${recordInPage == "3" ? 'selected' : ''}/>3
								<option value="5" ${recordInPage == "5" ? 'selected' : ''}/>5
								<option value="10" ${recordInPage == "10" ? 'selected' : ''}/>10					
						</select>
		 	</div>
		 </div>		
		</div>
		<!-- footer 2 -->
		<div class="panel-body">
			<table class="table table-bordered">
				<tr>
					<th><a href="sortById"><button value="idTask"
								class="th-button">Id Task</button> </a></th>
					<th><a href="sortByName"><button value="nameTask"
								class="th-button">Name Task</button> </a></th>
					<th>Time Update</th>
					<th>Status Task</th>
					<th>Public Task</th>
					<th>Acction Task</th>
				</tr>
				<c:forEach var="account" items="${list}" varStatus="status">
					<tr>
						<td>${account.idTask}</td>
						<td>${account.nameTask}</td>
						<td>${account.timeUpdate}</td>
						<td><select id = "select" onchange="changeStatus('${account.idTask}',this);">
								<option value="Done!" ${account.statusTask == "Done!" ? 'selected' : ''}/>Done!
								<option value="Doing..." ${account.statusTask == "Doing..." ? 'selected' : ''}/>Doing...
								<option value="Do not." ${account.statusTask == "Do not." ? 'selected' : ''}/>Do not.						
						</select> </td>
						<td><input type="checkbox" value="true"
							<c:if test="${account.publicTask}">checked</c:if>  onclick="clickChange('${account.idTask}')"/></td>
						<td><a href="editTask?idTask=${account.idTask}"><span class="glyphicon glyphicon-edit"></span></a>
							&nbsp;&nbsp;&nbsp;&nbsp; 
							<a href="deleteTask?idTask=${account.idTask}" onclick="clickDelete()"><span class="glyphicon glyphicon-remove"></span>
							</a></td>

					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- body -->
		<div class="panel-footer">

			<div class="row">
				<div class="col-sm-4">
					<ul class="pagination">
						<c:forEach var="page" begin="1" end="${maxPage}">
							<c:choose>
								<c:when test="${currentPage eq (page - 1)}">
									<li class="active"><a href="selectPage?page=${page-1}">${page}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="selectPage?page=${page-1}">${page}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</div>
				<div class="col-sm-4">
					<form action="search" method="post" class="form-inline">
						<input type="text" name="keyword"> <input type="submit"
							value="Search" class="btn btn-success btn-sm">
					</form>
				</div>
				<div class="col-sm-4">
					<a href="saveJob" class = "newTask" onclick="saveSQL()"><button>Save</button> </a>
				</div>
			</div>
		</div>
		<!-- footer 3 -->
	</div>
</body>
</html>
