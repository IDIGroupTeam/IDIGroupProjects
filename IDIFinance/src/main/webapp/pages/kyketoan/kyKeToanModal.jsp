<%@page import="com.idi.finance.bean.taikhoan.LoaiTaiKhoan"%>
<%@page import="com.idi.finance.bean.kyketoan.KyKeToan"%>
<%@page import="com.idi.finance.bean.doituong.DoiTuong"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<script type="text/javascript">
	//Shorthand for $( document ).ready()
	$(function() {
		function layCongNo() {
			var form = $(this).parents('form');

			console.log(form);

			var maTk = $(form).find("#loaiTaiKhoan\\.maTk").val();
			var maKkt = $(form).find("#kyKeToan\\.maKyKt").val();
			var loaiDt = $(form).find("#doiTuong\\.loaiDt").val();
			var maDt = $(form).find("#doiTuong\\.maDt").val();

			var param = "maTk=" + maTk + "&maKkt=" + maKkt + "&loaiDt="
					+ loaiDt + "&maDt=" + maDt;
			console.log("param", param);

			$.ajax({
				url : "${url}/kyketoan/soduky/congno",
				data : param,
				dataType : "json",
				type : "POST",
				success : function(soDuKy) {
					console.log("success", soDuKy);
					if (soDuKy != null) {
						console.log("success", soDuKy.noDauKy + " - "
								+ soDuKy.coDauKy);
						$(form).find("#noDauKy").val(soDuKy.noDauKy);
						$(form).find("#coDauKy").val(soDuKy.coDauKy);
					} else {
						console.log("success", "There is no data");
						$(form).find("#noDauKy").val(0);
						$(form).find("#coDauKy").val(0);
					}
				},
				error : function(data) {
					console.log(data);
					$(form).find("#noDauKy").val(0);
					$(form).find("#coDauKy").val(0);
				}
			});
		}

		function layTonKho() {
			var form = $(this).parents('form');

			console.log(form);

			var maTk = $(form).find("#loaiTaiKhoan\\.maTk").val();
			var maKkt = $(form).find("#kyKeToan\\.maKyKt").val();
			var maHh = $(form).find("#hangHoa\\.maHh").val();
			var maKho = $(form).find("#khoHang\\.maKho").val();

			var param = "maTk=" + maTk + "&maKkt=" + maKkt + "&maHh=" + maHh
					+ "&maKho=" + maKho;
			console.log("param", param);

			$.ajax({
				url : "${url}/kyketoan/soduky/tonkho",
				data : param,
				dataType : "json",
				type : "POST",
				success : function(soDuKy) {
					console.log("success", soDuKy);
					if (soDuKy != null) {
						console.log("success", soDuKy.noDauKy + " - "
								+ soDuKy.coDauKy);
						$(form).find("#noDauKy").val(soDuKy.noDauKy);
						$(form).find("#coDauKy").val(soDuKy.coDauKy);
						if (soDuKy.hangHoa != null) {
							$(form).find("#hangHoa\\.soLuong").val(
									soDuKy.hangHoa.soLuong);
							$(form).find("#hangHoa\\.donVi\\.maDv").val(
									soDuKy.hangHoa.donVi.maDv);
						}
					} else {
						console.log("success", "There is no data");
						$(form).find("#noDauKy").val(0);
						$(form).find("#coDauKy").val(0);
						$(form).find("#hangHoa\\.soLuong").val(0);
						$(form).find("#hangHoa\\.donVi\\.maDv").val(0);
					}
					$(form).find('#hangHoa\\.donVi\\.maDv').data('combobox')
							.refresh();
				},
				error : function(data) {
					console.log(data);
					$(form).find("#noDauKy").val(0);
					$(form).find("#coDauKy").val(0);
					$(form).find("#hangHoa\\.soLuong").val(0);
					$(form).find("#hangHoa\\.donVi\\.maDv").val(0);
					$(form).find('#hangHoa\\.donVi\\.maDv').data('combobox')
							.refresh();
				}
			});
		}

		$(".layCongNo").change(layCongNo);
		$(".layTonKho").change(layTonKho);

		$(".combox").combobox();

	});
</script>


<div id="sdTaiKhoanModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title text-primary">Thêm số dư tài khoản</h4>
			</div>
			<div class="modal-body">Thêm số dư tài khoản</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary">Lưu</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		</div>
	</div>
</div>

<div id="sdCongNoKHModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<form:form action="${url}/kyketoan/soduky/congnokh/luu"
				modelAttribute="mainFinanceFormKh" method="POST">
				<input type="hidden" id="kyKeToan.maKyKt" name="kyKeToan.maKyKt"
					value="${kyKeToan.maKyKt}" />
				<input type="hidden" id="doiTuong.loaiDt" name="doiTuong.loaiDt"
					value="${DoiTuong.KHACH_HANG}" />
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title text-primary">Thêm số dư công nợ khách
						hàng</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="doiTuong.maDt">Khách hàng</label>
						<form:select path="doiTuong.maDt"
							class="form-control combox layCongNo">
							<form:options items="${khDs}" itemLabel="tenKh" itemValue="maKh"></form:options>
						</form:select>
					</div>
					<div class="form-group">
						<label for="loaiTaiKhoan.maTk">Mã tài khoản công nợ</label>
						<form:select path="loaiTaiKhoan.maTk"
							class="form-control combox layCongNo">
							<form:option value=""></form:option>
							<form:options items="${congNoTkDs}" itemLabel="maTenTk"
								itemValue="maTk"></form:options>
						</form:select>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-sm-6">
								<label for="noDauKy">Nợ</label>
								<form:input path="noDauKy" class="form-control" />
							</div>
							<div class="col-sm-6">
								<label for="coDauKy">Có</label>
								<form:input path="coDauKy" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Lưu</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<div id="sdCongNoNCCModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<form:form action="${url}/kyketoan/soduky/congnoncc/luu"
				modelAttribute="mainFinanceFormNcc" method="POST">
				<input type="hidden" id="kyKeToan.maKyKt" name="kyKeToan.maKyKt"
					value="${kyKeToan.maKyKt}" />
				<input type="hidden" id="doiTuong.loaiDt" name="doiTuong.loaiDt"
					value="${DoiTuong.NHA_CUNG_CAP}" />
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title text-primary">Thêm số dư công nợ nhà
						cung cấp</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="doiTuong.maDt">Nhà cung cấp</label>
						<form:select path="doiTuong.maDt"
							class="form-control combox layCongNo">
							<form:options items="${nccDs}" itemLabel="tenNcc"
								itemValue="maNcc"></form:options>
						</form:select>
					</div>
					<div class="form-group">
						<label for="loaiTaiKhoan.maTk">Mã tài khoản công nợ</label>
						<form:select path="loaiTaiKhoan.maTk"
							class="form-control combox layCongNo">
							<form:option value=""></form:option>
							<form:options items="${congNoTkDs}" itemLabel="maTenTk"
								itemValue="maTk"></form:options>
						</form:select>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-sm-6">
								<label for="noDauKy">Nợ</label>
								<form:input path="noDauKy" class="form-control" />
							</div>
							<div class="col-sm-6">
								<label for="coDauKy">Có</label>
								<form:input path="coDauKy" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Lưu</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<div id="sdCongNoNVModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<form:form action="${url}/kyketoan/soduky/congnonv/luu"
				modelAttribute="mainFinanceFormNv" method="POST">
				<input type="hidden" id="kyKeToan.maKyKt" name="kyKeToan.maKyKt"
					value="${kyKeToan.maKyKt}" />
				<input type="hidden" id="doiTuong.loaiDt" name="doiTuong.loaiDt"
					value="${DoiTuong.NHAN_VIEN}" />
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title text-primary">Thêm số dư công nợ nhân
						viên</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="doiTuong.maDt">Nhân viên</label>
						<form:select path="doiTuong.maDt"
							class="form-control combox layCongNo">
							<form:options items="${nvDs}" itemLabel="fullName"
								itemValue="employeedId"></form:options>
						</form:select>
					</div>
					<div class="form-group">
						<label for="loaiTaiKhoan.maTk">Mã tài khoản công nợ</label>
						<form:select path="loaiTaiKhoan.maTk"
							class="form-control combox layCongNo">
							<form:option value=""></form:option>
							<form:options items="${congNoTkDs}" itemLabel="maTenTk"
								itemValue="maTk"></form:options>
						</form:select>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-sm-6">
								<label for="noDauKy">Nợ</label>
								<form:input path="noDauKy" class="form-control" />
							</div>
							<div class="col-sm-6">
								<label for="coDauKy">Có</label>
								<form:input path="coDauKy" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Lưu</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

<div id="sdTonKhoModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<form:form action="${url}/kyketoan/soduky/tonkhovthh/luu"
				modelAttribute="mainFinanceFormTkVthh" method="POST">
				<input type="hidden" id="kyKeToan.maKyKt" name="kyKeToan.maKyKt"
					value="${kyKeToan.maKyKt}" />
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title text-primary">Thêm số dư tồn kho VTHH</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="form-group col-sm-6">
							<label for="hangHoa.maHh">Hàng hóa</label>
							<form:select path="hangHoa.maHh"
								class="form-control combox layTonKho">
								<form:options items="${hangHoaDs}" itemLabel="tenHh"
									itemValue="maHh"></form:options>
							</form:select>
						</div>
						<div class="form-group col-sm-6">
							<label for="khoHang.maKho">Kho hàng</label>
							<form:select path="khoHang.maKho"
								class="form-control combox layTonKho">
								<form:options items="${khoHangDs}" itemLabel="tenKho"
									itemValue="maKho"></form:options>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-sm-6">
							<label for="hangHoa.donVi.maDv">Đơn vị</label>
							<form:select path="hangHoa.donVi.maDv"
								class="form-control combox">
								<form:options items="${donViDs}" itemLabel="tenDv"
									itemValue="maDv"></form:options>
							</form:select>
						</div>
						<div class="form-group col-sm-6">
							<label for="hangHoa.soLuong">Số lượng</label>
							<form:input path="hangHoa.soLuong" class="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-sm-12">
							<label for="loaiTaiKhoan.maTk">Mã tài khoản kho VTHH</label>
							<form:select path="loaiTaiKhoan.maTk"
								class="form-control combox layTonKho">
								<form:option value=""></form:option>
								<form:options items="${khoTkDs}" itemLabel="maTenTk"
									itemValue="maTk"></form:options>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-sm-6">
							<label for="noDauKy">Nợ</label>
							<form:input path="noDauKy" class="form-control" />
						</div>
						<div class="form-group col-sm-6">
							<label for="coDauKy">Có</label>
							<form:input path="coDauKy" class="form-control" />
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Lưu</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
				</div>
			</form:form>
		</div>
	</div>
</div>