<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	// Shorthand for $( document ).ready()
	$(function() {
		// Khởi tạo action/method cho mainFinanceForm form
		$("#mainFinanceForm").attr("action",
				"${url}/bctc/luuchuyentt/chitieu/danhsach");
		$("#mainFinanceForm").attr("method", "POST");

		$(".table")
				.cellEditable(
						{
							beforeLoad : function(param) {
								try {
									var rs = param.split(",");
									return "maTk=" + rs[2];
								} catch (e) {
								}
								return "";
							},
							urlLoad : "${url}/taikhoan/danhsach/capduoi",
							afterLoad : function(data) {
								if (data == null)
									return null;

								var list = new Array();
								for (var i = 0; i < data.length; i++) {
									var obj = new Object();
									obj.value = data[i].maTk;
									obj.label = data[i].maTenTk;
									list[i] = obj;
								}
								return list;
							},
							beforeSave : function(key, data) {
								try {
									var rs = key.split(",");
									res = "assetCode=" + rs[0] + "&maTkCu="
											+ rs[1];
									res += "&maTk=" + data;

									return res;
								} catch (e) {
								}
								return "";
							},
							urlSave : "${url}/bctc/luuchuyentt/chitieu/capnhat",
							afterSave : function(type, cell, data) {
								if (cell == null || data == null) {
									return;
								}

								if (type == "combobox") {
									var dataStr = data.assetCode;
									dataStr += "," + data.taiKhoanDs[0].maTk;
									dataStr += "," + data.taiKhoanDs[0].maTkGoc;
									$(cell).attr("data", dataStr);

									var res = data.taiKhoanDs[0].maTenTk;
									res = res.replace("-", "<br/>");
									$(cell).html(res);
								}
							},
							beforeRemove : function(key) {
								try {
									var rs = key.split(",");
									res = "assetCode=" + rs[0] + "&maTk="
											+ rs[1];

									return res;
								} catch (e) {
								}

								return "";
							},
							urlRemove : "${url}/bctc/luuchuyentt/chitieu/xoa",
							afterRemove : function(type, cell, data) {
								try {
									window.location.href = "${url}/bctc/luuchuyentt/chitieu/danhsach";
								} catch (e) {
									alert(e);
								}
							}
						});
	});
</script>

<h4>Danh sách các chỉ tiêu của bảng lưu chuyển tiền tệ</h4>

<div>
	<span><i>Theo thông tư 200</i></span> <a
		href="${url}/bctc/luuchuyentt/chitieu/taomoi"
		class="btn btn-info btn-sm pull-right"> <span
		class="glyphicon glyphicon-plus"></span> Tạo mới
	</a>
</div>
<br />

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<tbody>
			<c:forEach items="${bais}" var="topBai">
				<tr>
					<th colspan="7" class="text-left">${topBai.assetCode}-
						${topBai.assetName}</th>
				</tr>
				<c:choose>
					<c:when test="${topBai.childs.size()>0}">
						<c:forEach items="${topBai.childs}" var="bai">
							<!-- 50, 60, 61 -->
							<c:choose>
								<c:when test="${bai.childs.size()>0}">
									<tr>
										<th colspan="7" class="text-left">${bai.assetCode}-
											${bai.assetName}</th>
									</tr>
									<tr>
										<th rowspan="2" class="text-center">Chỉ tiêu</th>
										<th rowspan="2" class="text-center">Chỉ tiêu</th>
										<th colspan="2" class="text-center">Tài khoản</th>
										<th colspan="2" class="text-center">Tài khoản đối ứng</th>
										<th rowspan="2"></th>
									</tr>
									<tr>
										<th class="text-center">Tải khoản</th>
										<th class="text-center">Số dư</th>
										<th class="text-center">Tải khoản</th>
										<th class="text-center">Số dư</th>
									</tr>

									<c:forEach items="${bai.childs}" var="child">
										<!-- 20, 30, 40 -->
										<c:choose>
											<c:when test="${child.childs.size()>0}">
												<!-- 01, 02 ... 07 -->
												<!-- Dòng đầu tiên của dữ liệu cấp 1 -->
												<tr>
													<td style="width: 200px;" rowspan="${child.row}">${child.assetCode}<br />${child.assetName}</td>
													<td style="width: 200px;" rowspan="${child.childs[0].row}">${child.childs[0].assetCode}<br />${child.childs[0].assetName}</td>

													<td style="width: 200px;" class="cell-editable"
														type="combobox"
														data="${child.childs[0].assetCode},${child.childs[0].taiKhoanDs[0].maTk},${child.childs[0].taiKhoanDs[0].maTkGoc}">${child.childs[0].taiKhoanDs[0].maTk}<br />${child.childs[0].taiKhoanDs[0].tenTk}</td>
													<td><c:choose>
															<c:when
																test="${child.childs[0].taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
															<c:otherwise>Có</c:otherwise>
														</c:choose></td>
													<td style="width: 200px;">${child.childs[0].taiKhoanDs[0].doiUng.maTk}
														<br /> ${child.childs[0].taiKhoanDs[0].doiUng.tenTk}
													</td>
													<td><c:choose>
															<c:when
																test="${child.childs[0].taiKhoanDs[0].doiUng.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
															<c:otherwise>Có</c:otherwise>
														</c:choose></td>
												</tr>

												<c:forEach items="${child.childs[0].taiKhoanDs}" begin="1"
													end="${child.childs[0].taiKhoanDs.size()}" var="child1">
													<tr>
														<td class="cell-editable" type="combobox"
															data="${child.childs[0].assetCode},${child1.maTk},${child1.maTkGoc}">${child1.maTk}<br />${child1.tenTk}</td>
														<td><c:choose>
																<c:when test="${child1.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																<c:otherwise>Có</c:otherwise>
															</c:choose></td>
														<td>${child1.doiUng.maTk}<br />
															${child1.doiUng.tenTk}
														</td>
														<td><c:choose>
																<c:when
																	test="${child1.doiUng.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																<c:otherwise>Có</c:otherwise>
															</c:choose></td>
													</tr>
												</c:forEach>

												<!--  Các dòng tiếp theo -->
												<c:forEach items="${child.childs}" begin="1"
													end="${child.childs.size()}" var="child1">
													<tr>
														<td style="width: 200px;" rowspan="${child1.row}">${child1.assetCode}<br />${child1.assetName}</td>

														<td class="cell-editable" type="combobox"
															data="${child1.assetCode},${child1.taiKhoanDs[0].maTk},${child1.taiKhoanDs[0].maTkGoc}">${child1.taiKhoanDs[0].maTk}<br />${child1.taiKhoanDs[0].tenTk}</td>
														<td><c:choose>
																<c:when
																	test="${child1.taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																<c:otherwise>Có</c:otherwise>
															</c:choose></td>
														<td>${child1.taiKhoanDs[0].doiUng.maTk}<br />
															${child1.taiKhoanDs[0].doiUng.tenTk}
														</td>
														<td><c:choose>
																<c:when
																	test="${child1.taiKhoanDs[0].doiUng.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																<c:otherwise>Có</c:otherwise>
															</c:choose></td>
													</tr>

													<c:forEach items="${child1.taiKhoanDs}" begin="1"
														end="${child1.taiKhoanDs.size()}" var="child2">
														<tr>
															<td class="cell-editable" type="combobox"
																data="${child1.assetCode},${child2.maTk},${child2.maTkGoc}">${child2.maTk}<br />${child2.tenTk}</td>
															<td><c:choose>
																	<c:when test="${child2.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																	<c:otherwise>Có</c:otherwise>
																</c:choose></td>

															<td>${child2.doiUng.maTk}<br />
																${child2.doiUng.tenTk}
															</td>
															<td><c:choose>
																	<c:when
																		test="${child2.doiUng.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																	<c:otherwise>Có</c:otherwise>
																</c:choose></td>
														</tr>
													</c:forEach>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<!-- Ở đây còn thiếu trường hợp kiểu tra taiKhoanDs null hay không null -->
												<!-- Nhưng thực tế không có trường họp này nên không cài đặt cụ thể -->
												<tr>
													<td>${child.assetCode}<br />${child.assetName}</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty bai.taiKhoanDs}">
											<tr>
												<th class="text-left">${bai.assetCode}-${bai.assetName}</th>
												<th></th>
												<th style="width: 200px;" class="cell-editable"
													type="combobox"
													data="${bai.assetCode},${bai.taiKhoanDs[0].maTk},${bai.taiKhoanDs[0].maTkGoc}">${bai.taiKhoanDs[0].maTk}<br />${bai.taiKhoanDs[0].tenTk}</th>
												<th><c:choose>
														<c:when
															test="${bai.taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
														<c:otherwise>Có</c:otherwise>
													</c:choose></th>
												<th style="width: 200px;">${bai.taiKhoanDs[0].doiUng.maTk}
													<br /> ${bai.taiKhoanDs[0].doiUng.tenTk}
												</th>
												<th><c:choose>
														<c:when
															test="${bai.taiKhoanDs[0].doiUng.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
														<c:otherwise>Có</c:otherwise>
													</c:choose></th>
											</tr>

											<c:forEach items="${bai.taiKhoanDs}" begin="1"
												end="${bai.taiKhoanDs.size()}" var="child">
												<tr>
													<td></td>
													<td></td>
													<td class="cell-editable" type="combobox"
														data="${bai.assetCode},${child.maTk},${child.maTkGoc}">${child.maTk}<br />${child.tenTk}</td>
													<td><c:choose>
															<c:when test="${child.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
															<c:otherwise>Có</c:otherwise>
														</c:choose></td>

													<td>${child.doiUng.maTk}<br /> ${child.doiUng.tenTk}
													</td>
													<td><c:choose>
															<c:when
																test="${child.doiUng.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
															<c:otherwise>Có</c:otherwise>
														</c:choose></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<th class="text-left">${bai.assetCode}-${bai.assetName}</th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
												<th></th>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>