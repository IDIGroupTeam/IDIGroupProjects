<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://cewolf.sourceforge.net/taglib/cewolf.tld"
	prefix="cewolf"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {
		$('#dataTable').cellEditable({
			beforeLoad : {
				maBctc : function(data) {
					if (data == null)
						return null;

					var sendingData = new Object();
					return sendingData;
				}
			},
			afterLoad : {
				maBctc : function(data) {
					if (data == null)
						return null;

					var list = new Array();
					for (var i = 0; i < data.length; i++) {
						var obj = new Object();
						obj.value = data[i].maBctc;
						obj.label = data[i].tieuDe;
						list[i] = obj;
					}
					return list;
				}
			},
			beforeSave : {
				kpiKyBctc : function(data) {
					if (data == null)
						return null;

					var sendingData = new Object();
					sendingData.kpiKyKey = data.kpiKyKey;

					sendingData.bctc = new Object();
					sendingData.bctc.maBctc = eval(data.maBctc.value);
					if (isNaN(data.maBctc.maBctcCu)) {
						sendingData.bctc.maBctcCu = 0;
					} else {
						sendingData.bctc.maBctcCu = data.maBctc.maBctcCu;
					}

					return sendingData;
				}
			}
		});
	});
</script>

<h4>Cấu hình kỳ - báo cáo tài chính</h4>

<div class="table-responsive">
	<table id="dataTable" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center">Tháng</th>
				<th class="text-center">Báo cáo tài chính</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${kpiKyBctcs}" var="kpiKyBctc" varStatus="status">
				<tr data-save-url="${url}/quanly/bieudo/bctc/capnhat"
					data-name="kpiKyBctc" data-ma-bctc="${kpiKyBctc.bctc.maBctc}"
					data-kpi-ky-key="${kpiKyBctc.kpiKy.key}">
					<td>${kpiKyBctc.kpiKy.value}</td>
					<td><span class="cell-editable dis-removable"
						data-type="combobox" data-field="maBctc"
						data-ma-bctc-cu="${kpiKyBctc.bctc.maBctc}"
						data-load-url="${url}/bctc/danhsach/${kyKeToan.maKyKt}">${kpiKyBctc.bctc.tieuDe}</span></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>