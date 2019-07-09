function docKyKeToanDs(kyKeToanDsStr) {
	kyKeToanDsStr = $.trim(kyKeToanDsStr);
	kyKeToanDsStr = kyKeToanDsStr.substring(1, kyKeToanDsStr.length - 1);

	var kyKeToanDsTmpl = kyKeToanDsStr.split(", ");
	var kyKeToanDs = new Array();
	for (var i = 0; i < kyKeToanDsTmpl.length; i++) {
		var kyKeToan = new Object();
		var kyKeToanTmpl = kyKeToanDsTmpl[i].split(" ");
		kyKeToan.maKyKt = $.trim(kyKeToanTmpl[0]);
		kyKeToan.batDau = $.trim(kyKeToanTmpl[1]);
		kyKeToan.ketThuc = $.trim(kyKeToanTmpl[2]);
		kyKeToanDs[i] = kyKeToan;
	}

	return kyKeToanDs;
}