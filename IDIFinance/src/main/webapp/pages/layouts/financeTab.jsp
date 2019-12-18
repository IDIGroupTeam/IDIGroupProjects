<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>

<ul class="main-tab nav nav-tabs nav-pills nav-justified">
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Biểu đồ KPI<span class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<c:forEach items="${kpiGroups}" var="kpiGroup">
				<li id="tab${kpiGroup.groupId}"><a
					href="${url}/bieudo/${kpiGroup.groupId}">${kpiGroup.groupName}</a></li>
			</c:forEach>
			<li class="divider"></li>
			<li id="tabQLBD"><a href="${url}/quanly/bieudo">Quản lý biểu
					đồ</a></li>
			<li id="tabQLBDBCTC"><a href="${url}/quanly/bieudo/bctc">Cấu
					hình bctc</a></li>
		</ul></li>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Chứng từ kế toán<span
			class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<li id="tabCTPT"><a href="${url}/chungtu/phieuthu/danhsach">Phiếu
					thu</a></li>
			<li id="tabCTPC"><a href="${url}/chungtu/phieuchi/danhsach">Phiếu
					chi</a></li>
			<li id="tabCTBC"><a href="${url}/chungtu/baoco/danhsach">Báo
					có</a></li>
			<li id="tabCTBN"><a href="${url}/chungtu/baono/danhsach">Báo
					nợ</a></li>
			<li id="tabCTKTTH"><a href="${url}/chungtu/ktth/danhsach">Kế
					toán tổng hợp</a></li>
			<li id="tabCTMH"><a href="${url}/chungtu/muahang/danhsach">Mua
					hàng</a></li>
			<li id="tabCTBH"><a href="${url}/chungtu/banhang/danhsach">Bán
					hàng</a></li>
			<li id="tabCTKC"><a href="${url}/chungtu/ketchuyen/danhsach">Kết
					chuyển</a></li>
		</ul></li>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Sổ kế toán<span class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<li id="tabSKTNKC"><a href="${url}/soketoan/nhatkychung">Nhật
					ký chung</a></li>
			<li id="tabSKTSC"><a href="${url}/soketoan/socai">Sổ cái</a></li>
			<li id="tabSKTSTHCN"><a href="${url}/soketoan/socongno/tonghop">Sổ
					tổng hợp công nợ</a></li>
			<li id="tabSKTSCTCN"><a href="${url}/soketoan/socongno/chitiet">Sổ
					chi tiết công nợ</a></li>
			<li id="tabSKTSTHNXT"><a
				href="${url}/soketoan/nhapxuatton/tonghop">Sổ tổng hợp nhập xuất
					tồn</a></li>
			<li id="tabSKTSCTNXT"><a
				href="${url}/soketoan/nhapxuatton/chitiet">Sổ chi tiết nhập xuất
					tồn</a></li>
		</ul></li>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Báo cáo tài chính<span
			class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<li id="tabBCTCDS"><a href="${url}/bctc/danhsach">Báo cáo
					tài chính</a></li>
			<%-- <li id="tabBCDKT"><a href="${url}/bctc/cdkt/danhsach">Bảng
					cân đối kế toán</a></li> --%>
			<li id="tabBCDPS"><a href="${url}/bctc/candoiphatsinh">Bảng
					cân đối phát sinh</a></li>
			<%-- <li id="tabBKQHDKD"><a href="${url}/bctc/kqhdkd/danhsach">Bảng
					kết quả HDKD</a></li>
			<li id="tabBLCTT"><a href="${url}/bctc/luuchuyentt/danhsach">Bảng
					lưu chuyển tiền tệ</a></li> --%>
		</ul></li>
	<%-- <li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Nghiệp vụ<span class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<li id="tabDSNV"><a href="${url}/congviec/nghiepvu/danhsach">Quản
					lý nghiệp vụ</a></li>
			<li id="tabDSLV"><a href="${url}/congviec/linhvuc/danhsach">Lĩnh
					vực</a></li>
		</ul></li> --%>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Quản trị<span class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<li id="tabKKT"><a href="${url}/kyketoan/danhsach">Kỳ kế
					toán</a></li>
			<li id="tabKKT"><a href="${url}/kyketoan/xem/1">Khai báo số
					dư đầu kỳ</a></li>
			<li id="tabDSCH"><a href="${url}/cauhinh/danhsach">Cấu hình</a></li>

			<li class="divider"></li>
			<li id="tabDMTK"><a href="${url}/taikhoan/danhsach">Danh
					sách tài khoản</a></li>
			<li id="tabDSBCDKT"><a href="${url}/bctc/cdkt/chitieu/danhsach">Danh
					sách chỉ tiêu CDKT</a></li>
			<li id="tabDSBKQHDKD"><a
				href="${url}/bctc/kqhdkd/chitieu/danhsach">Danh sách chỉ tiêu
					KQHDKD</a></li>
			<li id="tabDSBLCTT"><a
				href="${url}/bctc/luuchuyentt/chitieu/danhsach">Danh sách chỉ
					tiêu LCTT</a></li>

			<li class="divider"></li>
			<li id="tabDSHH"><a href="${url}/hanghoa/danhsach">Danh sách
					hàng hóa</a></li>
			<li id="tabDSNHH"><a href="${url}/hanghoa/nhom/danhsach">Nhóm
					hàng hóa</a></li>
			<li id="tabDSDVHH"><a href="${url}/hanghoa/donvi/danhsach">Đơn
					vị tính</a></li>
			<li id="tabDSK"><a href="${url}/hanghoa/kho/danhsach">Kho
					bãi</a></li>

			<li class="divider"></li>
			<li id="tabDSHH"><a href="#">Danh sách tài sản</a></li>

			<li class="divider"></li>
			<li id="tabDSNH"><a href="${url}/nganhang/danhsach">Danh
					sách ngân hàng</a></li>
			<li id="tabDSNHTK"><a href="${url}/nganhang/taikhoan/danhsach">Danh
					sách tài khoản ngân hàng</a></li>
			<li id="tabDSLT"><a href="${url}/loaitien/danhsach">Danh
					sách loại tiền</a></li>

			<%-- <li id="tabCNDL"><a href="${url}/bctc/capnhatdulieu">Cập nhật dữ liệu</a></li> --%>
		</ul></li>
</ul>