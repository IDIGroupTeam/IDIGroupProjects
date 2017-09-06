<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table width="100%">
	<tr>
		<td align="left" width="40%">
			<div class="form-group">
				<label for="sel1">Chỉ số KPI</label> <select id="kpiList"
					class="form-control">
					<option value="vqkhoanphaithu">Vòng quay khoản phải thu</option>
					<option value="kttienbinhquan">Kỳ thu tiền bình quân</option>
					<option value="vqhangtonkho_sosach">Vòng quay hàng tồn kho theo giá trị sổ sách</option>
					<option value="vqhangtonkho_thitruong">Vòng quay hàng tồn kho theo giá thị trường</option>
					<option value="sntonkhobinhquan_sosach">Số ngày tồn kho bình quân theo giá trị sổ sách</option>
					<option value="sntonkhobinhquan_thitruong">Số ngày hàng tồn kho  bình quân theo giá thị trường</option>
					<option value="chukyhoatdong_sosach">Chu kỳ hoạt động của doanh nghiệp theo giá trị sổ sách</option>
					<option value="chukyhoatdong_thitruong">Chu kỳ hoạt động của doanh nghiệp theo giá thị trường</option>
					<option value="vqkhoanphaitra">Vòng quay khoản phải trả</option>
					<option value="kyphaitrabinhquan">Kỳ phải trả bình quân</option>
					<option value="ckluanchuyentien_sosach">Chu kỳ luân chuyển tiền theo giá trị sổ sách</option>
					<option value="ckluanchuyentien_thitruong">Chu kỳ luân chuyển tiền theo giá trị thị trường</option>
					<option value="knttlaivay">Khả năng thanh toán lãi vay</option>
				</select>
			</div>
		</td>
		<td></td>
	</tr>
</table>