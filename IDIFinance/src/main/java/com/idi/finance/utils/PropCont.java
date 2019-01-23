package com.idi.finance.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.idi.finance.bean.CauHinh;
import com.idi.finance.dao.CauHinhDAO;

public class PropCont {
	private static final Logger logger = Logger.getLogger(PropCont.class);

	@Autowired
	private CauHinhDAO cauHinhDAO;

	// Dưới đây là các tham số cấu hình
	// Được đọc từ file properties vào
	@Value("${SO_DU_DAU_KY_EXCEL_TEN_FILE}")
	private String VALUE_SO_DU_DAU_KY_EXCEL_TEN_FILE;

	// Giá trị mặc định của một số thông tin chung
	// Đã có cấu hình trong db, ở đây lưu giá trị mặc định nếu db ko có
	@Value("${CHU_TICH}")
	private String VALUE_CHU_TICH;
	@Value("${DIA_CHI}")
	private String VALUE_DIA_CHI;
	@Value("${GIAM_DOC}")
	private String VALUE_GIAM_DOC;
	@Value("${KE_TOAN_TRUONG}")
	private String VALUE_KE_TOAN_TRUONG;
	@Value("${MST}")
	private String VALUE_MST;
	@Value("${TEN_CONG_TY}")
	private String VALUE_TEN_CONG_TY;
	@Value("${THU_KHO}")
	private String VALUE_THU_KHO;
	@Value("${THU_QUY}")
	private String VALUE_THU_QUY;
	@Value("${CHE_DO_KE_TOAN}")
	private String VALUE_CHE_DO_KE_TOAN;

	// Giá trị mặc định cho danh sách một số tài khoản của các chứng từ
	// Đã có cấu hình trong db, ở đây lưu giá trị mặc định nếu db ko có
	@Value("${PHIEU_THU_DS_TK_NO}")
	private String VALUE_PHIEU_THU_DS_TK_NO;
	@Value("${PHIEU_CHI_DS_TK_CO}")
	private String VALUE_PHIEU_CHI_DS_TK_CO;

	@Value("${BAO_NO_DS_TK_NO}")
	private String VALUE_BAO_NO_DS_TK_NO;
	@Value("${BAO_CO_DS_TK_CO}")
	private String VALUE_BAO_CO_DS_TK_CO;

	@Value("${MUA_HANG_DS_TK_KHO_NO}")
	private String VALUE_MUA_HANG_DS_TK_KHO_NO;
	@Value("${MUA_HANG_DS_TK_THANH_TOAN_CO}")
	private String VALUE_MUA_HANG_DS_TK_THANH_TOAN_CO;
	@Value("${MUA_HANG_DS_TK_GTGT_NO}")
	private String VALUE_MUA_HANG_DS_TK_GTGT_NO;
	@Value("${MUA_HANG_DS_TK_NK_CO}")
	private String VALUE_MUA_HANG_DS_TK_NK_CO;
	@Value("${MUA_HANG_DS_TK_TTDB_CO}")
	private String VALUE_MUA_HANG_DS_TK_TTDB_CO;

	@Value("${BAN_HANG_DS_TK_THANH_TOAN_NO}")
	private String VALUE_BAN_HANG_DS_TK_THANH_TOAN_NO;
	@Value("${BAN_HANG_DS_TK_DOANH_THU_CO}")
	private String VALUE_BAN_HANG_DS_TK_DOANH_THU_CO;
	@Value("${BAN_HANG_DS_TK_GIA_VON_NO}")
	private String VALUE_BAN_HANG_DS_TK_GIA_VON_NO;
	@Value("${BAN_HANG_DS_TK_KHO_NO}")
	private String VALUE_BAN_HANG_DS_TK_KHO_NO;
	@Value("${BAN_HANG_DS_TK_GTGT_CO}")
	private String VALUE_BAN_HANG_DS_TK_GTGT_CO;
	@Value("${BAN_HANG_DS_TK_XK_CO}")
	private String VALUE_BAN_HANG_DS_TK_XK_CO;

	// Cấu hình khác
	@Value("${TAI_KHOAN_CONG_NO}")
	private String VALUE_TAI_KHOAN_CONG_NO;
	@Value("${TAI_KHOAN_KHO_VTHH}")
	private String VALUE_TAI_KHOAN_KHO_VTHH;

	// Các khóa
	public static final String SO_DU_DAU_KY_EXCEL_TEN_FILE = "SO_DU_DAU_KY_EXCEL_TEN_FILE";

	public static final String TEN_CONG_TY = "TEN_CONG_TY";
	public static final String DIA_CHI = "DIA_CHI";
	public static final String CHU_TICH = "CHU_TICH";
	public static final String GIAM_DOC = "GIAM_DOC";
	public static final String KE_TOAN_TRUONG = "KE_TOAN_TRUONG";
	public static final String THU_KHO = "THU_KHO";
	public static final String THU_QUY = "THU_QUY";
	public static final String MST = "MST";
	public static final String CHE_DO_KE_TOAN = "CHE_DO_KE_TOAN";

	public static final String PHIEU_THU_DS_TK_NO = "PHIEU_THU_DS_TK_NO";
	public static final String PHIEU_CHI_DS_TK_CO = "PHIEU_CHI_DS_TK_CO";

	public static final String BAO_NO_DS_TK_NO = "BAO_NO_DS_TK_NO";
	public static final String BAO_CO_DS_TK_CO = "BAO_CO_DS_TK_CO";

	public static final String MUA_HANG_DS_TK_KHO_NO = "MUA_HANG_DS_TK_KHO_NO";
	public static final String MUA_HANG_DS_TK_THANH_TOAN_CO = "MUA_HANG_DS_TK_THANH_TOAN_CO";
	public static final String MUA_HANG_DS_TK_GTGT_NO = "MUA_HANG_DS_TK_GTGT_NO";
	public static final String MUA_HANG_DS_TK_NK_CO = "MUA_HANG_DS_TK_NK_CO";
	public static final String MUA_HANG_DS_TK_TTDB_CO = "MUA_HANG_DS_TK_TTDB_CO";

	public static final String BAN_HANG_DS_TK_THANH_TOAN_NO = "BAN_HANG_DS_TK_THANH_TOAN_NO";
	public static final String BAN_HANG_DS_TK_DOANH_THU_CO = "BAN_HANG_DS_TK_DOANH_THU_CO";
	public static final String BAN_HANG_DS_TK_GIA_VON_NO = "BAN_HANG_DS_TK_GIA_VON_NO";
	public static final String BAN_HANG_DS_TK_KHO_NO = "BAN_HANG_DS_TK_KHO_NO";
	public static final String BAN_HANG_DS_TK_GTGT_CO = "BAN_HANG_DS_TK_GTGT_CO";
	public static final String BAN_HANG_DS_TK_XK_CO = "BAN_HANG_DS_TK_XK_CO";

	public static final String TAI_KHOAN_CONG_NO = "TAI_KHOAN_CONG_NO";
	public static final String TAI_KHOAN_KHO_VTHH = "TAI_KHOAN_KHO_VTHH";

	private HashMap<String, String> propConts = new HashMap<>();

	public void init() {
		propConts = new HashMap<>();

		propConts.put(SO_DU_DAU_KY_EXCEL_TEN_FILE, VALUE_SO_DU_DAU_KY_EXCEL_TEN_FILE);

		propConts.put(TEN_CONG_TY, VALUE_TEN_CONG_TY);
		propConts.put(DIA_CHI, VALUE_DIA_CHI);
		propConts.put(CHU_TICH, VALUE_CHU_TICH);
		propConts.put(GIAM_DOC, VALUE_GIAM_DOC);
		propConts.put(KE_TOAN_TRUONG, VALUE_KE_TOAN_TRUONG);
		propConts.put(THU_KHO, VALUE_THU_KHO);
		propConts.put(THU_QUY, VALUE_THU_QUY);
		propConts.put(MST, VALUE_MST);
		propConts.put(CHE_DO_KE_TOAN, CHE_DO_KE_TOAN);

		propConts.put(PHIEU_THU_DS_TK_NO, VALUE_PHIEU_THU_DS_TK_NO);
		propConts.put(PHIEU_CHI_DS_TK_CO, VALUE_PHIEU_CHI_DS_TK_CO);

		propConts.put(BAO_NO_DS_TK_NO, VALUE_BAO_NO_DS_TK_NO);
		propConts.put(BAO_CO_DS_TK_CO, VALUE_BAO_CO_DS_TK_CO);

		propConts.put(MUA_HANG_DS_TK_KHO_NO, VALUE_MUA_HANG_DS_TK_KHO_NO);
		propConts.put(MUA_HANG_DS_TK_THANH_TOAN_CO, VALUE_MUA_HANG_DS_TK_THANH_TOAN_CO);
		propConts.put(MUA_HANG_DS_TK_GTGT_NO, VALUE_MUA_HANG_DS_TK_GTGT_NO);
		propConts.put(MUA_HANG_DS_TK_NK_CO, VALUE_MUA_HANG_DS_TK_NK_CO);
		propConts.put(MUA_HANG_DS_TK_TTDB_CO, VALUE_MUA_HANG_DS_TK_TTDB_CO);

		propConts.put(BAN_HANG_DS_TK_THANH_TOAN_NO, VALUE_BAN_HANG_DS_TK_THANH_TOAN_NO);
		propConts.put(BAN_HANG_DS_TK_DOANH_THU_CO, VALUE_BAN_HANG_DS_TK_DOANH_THU_CO);
		propConts.put(BAN_HANG_DS_TK_GIA_VON_NO, VALUE_BAN_HANG_DS_TK_GIA_VON_NO);
		propConts.put(BAN_HANG_DS_TK_KHO_NO, VALUE_BAN_HANG_DS_TK_KHO_NO);
		propConts.put(BAN_HANG_DS_TK_GTGT_CO, VALUE_BAN_HANG_DS_TK_GTGT_CO);
		propConts.put(BAN_HANG_DS_TK_XK_CO, VALUE_BAN_HANG_DS_TK_XK_CO);

		propConts.put(TAI_KHOAN_CONG_NO, VALUE_TAI_KHOAN_CONG_NO);
		propConts.put(TAI_KHOAN_KHO_VTHH, VALUE_TAI_KHOAN_KHO_VTHH);
	}

	public HashMap<String, Object> getCauHinhTheoNhom(int nhom) {
		HashMap<String, Object> rsMap = null;

		List<CauHinh> cauHinhDs = cauHinhDAO.danhSachCauHinh(nhom);
		if (cauHinhDs != null && cauHinhDs.size() > 0) {
			rsMap = new HashMap<>();
			Iterator<CauHinh> iter = cauHinhDs.iterator();
			while (iter.hasNext()) {
				CauHinh cauHinh = iter.next();
				rsMap.put(cauHinh.getMa(), cauHinh);
			}
		}

		return rsMap;
	}

	public CauHinh getCauHinh(String key) {
		// Lấy từ csdl
		CauHinh cauHinh = cauHinhDAO.layCauHinh(key);

		// Nếu không có thì lấy từ file cấu hình
		if (cauHinh == null) {
			String value = propConts.get(key);

			if (value != null && !value.trim().isEmpty()) {
				cauHinh = new CauHinh();
				cauHinh.setMa(key);
				cauHinh.setGiaTri(value);
			}
		}

		return cauHinh;
	}
}
