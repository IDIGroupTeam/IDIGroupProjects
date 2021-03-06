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
		$("#mainFinanceForm").attr("action", "${url}/cdkt/candoiketoan");
		$("#mainFinanceForm").attr("method", "POST");
	});
</script>

<h4>Danh sách các mã số của bảng cân đối kế toán</h4>

<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<tbody>
			<c:forEach items="${bais}" var="topBai">
				<tr>
					<th colspan="5" class="text-center">${topBai.assetName}<br />${topBai.assetCode}</th>
				</tr>
				<c:choose>
					<c:when test="${topBai.childs.size()>0}">
						<c:forEach items="${topBai.childs}" var="bai">
							<tr>
								<th colspan="5" class="text-center">${bai.assetName}<br />${bai.assetCode}</th>
							</tr>
							<tr>
								<th rowspan="2" class="text-center">Chi tiêu</th>
								<th rowspan="2" class="text-center">Tài khoản</th>
								<th rowspan="2" class="text-center">Tài khoản</th>
								<th colspan="2" class="text-center">Tài khoản</th>
							</tr>
							<tr>
								<th class="text-center">Tải khoản</th>
								<th class="text-center">Số dư</th>
							</tr>

							<c:choose>
								<c:when test="${bai.childs.size()>0}">
									<c:forEach items="${bai.childs}" var="child">
										<c:choose>
											<c:when test="${child.childs.size()>0}">
												<!-- Dòng đầu tiên của dữ liệu cấp 1 -->
												<tr>
													<td rowspan="${child.row}">${child.assetCode}<br />${child.assetName}</td>
													<td rowspan="${child.childs[0].row}">${child.childs[0].assetCode}<br />${child.childs[0].assetName}</td>
													<c:choose>
														<c:when test="${child.childs[0].childs.size()>0}">
															<td rowspan="${child.childs[0].childs[0].row}">
																${child.childs[0].childs[0].assetCode}<br />${child.childs[0].childs[0].assetName}
															</td>
															<td>${child.childs[0].childs[0].taiKhoanDs[0].maTk}<br />${child.childs[0].childs[0].taiKhoanDs[0].tenTk}</td>
															<td><c:choose>
																	<c:when
																		test="${child.childs[0].childs[0].taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																	<c:otherwise>Có</c:otherwise>
																</c:choose></td>
														</c:when>
														<c:otherwise>
															<td></td>
															<td>${child.childs[0].taiKhoanDs[0].maTk}<br />${child.childs[0].taiKhoanDs[0].tenTk}</td>
															<td><c:choose>
																	<c:when
																		test="${child.childs[0].taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																	<c:otherwise>Có</c:otherwise>
																</c:choose></td>
														</c:otherwise>
													</c:choose>
												</tr>

												<c:choose>
													<c:when test="${child.childs[0].childs.size()>0}">
														<c:forEach items="${child.childs[0].childs[0].taiKhoanDs}"
															begin="1"
															end="${child.childs[0].childs[0].taiKhoanDs.size()}"
															var="child1">
															<tr>
																<td>${child1.maTk}<br />${child1.tenTk}</td>
																<td><c:choose>
																		<c:when test="${child1.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																		<c:otherwise>Có</c:otherwise>
																	</c:choose></td>
															</tr>
														</c:forEach>
														<c:forEach items="${child.childs[0].childs}" begin="1"
															end="${child.childs[0].childs.size()}" var="child1">
															<tr>
																<td rowspan="${child1.row}">${child1.assetCode}<br />${child1.assetName}
																</td>
																<td>${child1.taiKhoanDs[0].maTk}<br />${child1.taiKhoanDs[0].tenTk}</td>
																<td><c:choose>
																		<c:when
																			test="${child1.taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																		<c:otherwise>Có</c:otherwise>
																	</c:choose></td>
															</tr>
															<c:forEach items="${child1.taiKhoanDs}" begin="1"
																end="${child1.taiKhoanDs.size()}" var="child2">
																<tr>
																	<td>${child2.maTk}<br />${child2.tenTk}</td>
																	<td><c:choose>
																			<c:when test="${child2.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																			<c:otherwise>Có</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:forEach items="${child.childs[0].taiKhoanDs}" begin="1"
															end="${child.childs[0].taiKhoanDs.size()}" var="child1">
															<tr>
																<td></td>
																<td>${child1.maTk}<br />${child1.tenTk}</td>
																<td><c:choose>
																		<c:when test="${child1.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																		<c:otherwise>Có</c:otherwise>
																	</c:choose></td>
															</tr>
														</c:forEach>
													</c:otherwise>
												</c:choose>

												<!--  Các dòng tiếp theo -->
												<c:forEach items="${child.childs}" begin="1"
													end="${child.childs.size()}" var="child1">
													<tr>
														<td rowspan="${child1.row}">${child1.assetCode}<br />${child1.assetName}</td>
														<c:choose>
															<c:when test="${child1.childs.size()>0}">
																<td rowspan="${child1.childs[0].row}">
																	${child1.childs[0].assetCode}<br />${child1.childs[0].assetName}
																</td>
																<td>${child1.childs[0].taiKhoanDs[0].maTk}<br />${child1.childs[0].taiKhoanDs[0].tenTk}</td>
																<td><c:choose>
																		<c:when
																			test="${child1.childs[0].taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																		<c:otherwise>Có</c:otherwise>
																	</c:choose></td>
															</c:when>
															<c:otherwise>
																<td></td>
																<td>${child1.taiKhoanDs[0].maTk}<br />${child1.taiKhoanDs[0].tenTk}</td>
																<td><c:choose>
																		<c:when
																			test="${child1.taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																		<c:otherwise>Có</c:otherwise>
																	</c:choose></td>
															</c:otherwise>
														</c:choose>
													</tr>
													<c:choose>
														<c:when test="${child1.childs.size()>0}">
															<c:forEach items="${child1.childs[0].taiKhoanDs}"
																begin="1" end="${child1.childs[0].taiKhoanDs.size()}"
																var="child2">
																<tr>
																	<td>${child2.maTk}<br />${child2.tenTk}</td>
																	<td><c:choose>
																			<c:when test="${child2.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																			<c:otherwise>Có</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
															<c:forEach items="${child1.childs}" begin="1"
																end="${child1.childs.size()}" var="child2">
																<tr>
																	<td rowspan="${child2.row}">${child2.assetCode}<br />${child2.assetName}
																	</td>
																	<td>${child2.taiKhoanDs[0].maTk}<br />${child2.taiKhoanDs[0].tenTk}</td>
																	<td><c:choose>
																			<c:when
																				test="${child2.taiKhoanDs[0].soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																			<c:otherwise>Có</c:otherwise>
																		</c:choose></td>
																</tr>
																<c:forEach items="${child2.taiKhoanDs}" begin="1"
																	end="${child2.taiKhoanDs.size()}" var="child3">
																	<tr>
																		<td>${child3.maTk}<br />${child3.tenTk}</td>
																		<td><c:choose>
																				<c:when test="${child3.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																				<c:otherwise>Có</c:otherwise>
																			</c:choose></td>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<c:forEach items="${child1.taiKhoanDs}" begin="1"
																end="${child1.taiKhoanDs.size()}" var="child2">
																<tr>
																	<td></td>
																	<td>${child2.maTk}<br />${child2.tenTk}</td>
																	<td><c:choose>
																			<c:when test="${child2.soDuGiaTri==LoaiTaiKhoan.NO}">Nợ</c:when>
																			<c:otherwise>Có</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td>${child.assetCode}<br />${child.assetName}</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
							</c:choose>
						</c:forEach>
					</c:when>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>