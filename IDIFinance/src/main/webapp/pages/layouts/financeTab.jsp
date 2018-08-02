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
			<li id="tabQLBD"><a href="${url}/quanlybieudo">Quản lý biểu
					đồ</a></li>
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
			<li id="tabSKTSCN"><a href="${url}/soketoan/sothcongno">Sổ
					tổng hợp công nợ</a></li>
			<li id="tabSKTSCN"><a href="${url}/soketoan/soctcongno">Sổ
					chi tiết công nợ</a></li>
		</ul></li>
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#">Báo cáo tài chính<span
			class="caret"></span>
	</a>
		<ul class="dropdown-menu">
			<li id="tabBCDKT"><a href="${url}/cdkt/candoiketoan">Bảng
					cân đối kế toán</a></li>
			<li id="tabBCDPS"><a href="${url}/bctc/candoiphatsinh">Bảng
					cân đối phát sinh</a></li>
			<li id="tabBKQHDKD"><a href="${url}/cdkt/ketquahdkd">Bảng
					kết quả HDKD</a></li>
			<li><a href="#">Bảng lưu chuyển tiền tệ</a></li>

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

			<li class="divider"></li>
			<li id="tabDSNT"><a href="#">Danh sách ngoại tệ</a></li>
			<li id="tabDSBCDKT"><a href="${url}/cdkt/danhsachtaikhoan">Danh
					sách BCDKT</a></li>
			<li id="tabDMTK"><a href="${url}/taikhoan/danhsach">Danh
					sách tài khoản</a></li>

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
			<li id="tabDSNH"><a href="${url}/nganhang/danhsach">Danh
					sách ngân hàng</a></li>
			<li id="tabDSNHTK"><a href="${url}/nganhang/taikhoan/danhsach">Danh
					sách tài khoản ngân hàng</a></li>

			<li id="tabCNDL"><a href="${url}/cdkt/capnhatdulieu">Cập
					nhật dữ liệu</a></li>
		</ul></li>
</ul>