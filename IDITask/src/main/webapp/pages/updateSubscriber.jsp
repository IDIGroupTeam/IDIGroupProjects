<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="bootstrap-tab-panel"
	href="${url}/public/css/bootstrap-tab-panel.css" />
<html>
<body>
	<hr></hr>
	<div class="container">
		<h2>Example tab 2 (using standard nav-tabs)</h2>
	</div>

	<div id="exTab2" class="container">
		<ul class="nav nav-tabs">
			<li><a href="#1" data-toggle="tab">Thông tin chung</a></li>
			<li class="active"><a href="#2" data-toggle="tab">Người liên
					quan</a></li>
			<li><a href="#3" data-toggle="tab">Công việc liên quan</a></li>
		</ul>

		<div class="tab-content ">
			<div class="tab-pane active" id="1">
				<h3>Standard tab panel created on bootstrap using nav-tabs</h3>
			</div>
			<div class="tab-pane" id="2">
				<table class="table table-bordered" >
					<tr>
						<c:if test="${sub != null}">
							<th>Người liên quan đã chọn</th>
						</c:if>
						<th>Chọn thêm người liên quan</th>
					</tr>
					<tr>
						<c:if test="${sub != null}">
							<td><select name="subscribers" class="form-control animated"
								multiple="multiple" disabled="disabled" size="5">
									<c:forEach items="${subscriberList}" var="subscriber">
										<option value="${subscriber.employeeId}">${subscriber.fullName},&nbsp;${subscriber.jobTitle}
										</option>
									</c:forEach>
							</select></td>
						</c:if>
						<td><select name="employees" class="form-control animated"
							multiple="multiple" size="5">
								<c:forEach items="${employeesList}" var="employee">
									<option value="${employee.employeeId}">${employee.fullName},&nbsp;${employee.jobTitle}
									</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
				<input class="btn btn-lg btn-primary btn-sm" type="submit"
					value="Lưu" name="Lưu" />
			</div>
			<div class="tab-pane" id="3">
				<h3>add clearfix to tab-content (see the css)</h3>
			</div>
		</div>
	</div>

	<hr></hr>

	<!-- Bootstrap core JavaScript ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

</body>
</html>