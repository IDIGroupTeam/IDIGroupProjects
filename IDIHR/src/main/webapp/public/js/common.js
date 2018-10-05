function summitForm(formId, dispatch) {
	try {
		var form = document.getElementById(formId);
		var dispatchElem = document.getElementsByName("dispatch")[0];

		if (dispatch != null || dispatch != '') {

			if (dispatchElem != null) {
				dispatchElem.value = dispatch;
			} else
				form.action = "search.do?dispatch=" + dispatch;

			form.submit();
		} else {
			form.submit();
		}
	} catch (e) {
		alert('Javascript Error: ' + e.message);
	}
}

function nextPage(formId, dispatch) {
	var cust = document.getElementById('custSelection');
	if (cust.value == null || cust.value == '') {
		alert('No customer is selected, please select a customer.');
	} else
		summitForm(formId, dispatch)
}

function showWaitingBox() {

	center1("waitingBox");
	elWaitingBox = document.getElementById("waitingBox");
	elWaitingBox.style.visibility = "visible";

	var browser = navigator.appName;
	if (browser != "Microsoft Internet Explorer")
		setTimeout("elWaitingBox.style.visibility = 'hidden'", 6000);
	// else
	// setTimeout("elWaitingBox.style.visibility = 'hidden'", 100000);
}

function closeWaitingBox() {
	elWaitingBox = document.getElementById("waitingBox");
	elWaitingBox.style.visibility = "hidden";
}

function getScrollXY() {
	var scrOfX = 0, scrOfY = 0;
	if (typeof (window.pageYOffset) == 'number') {
		//Netscape compliant
		scrOfY = window.pageYOffset;
		scrOfX = window.pageXOffset;
	} else if (document.body
			&& (document.body.scrollLeft || document.body.scrollTop)) {
		//DOM compliant
		scrOfY = document.body.scrollTop;
		scrOfX = document.body.scrollLeft;
	} else if (document.documentElement
			&& (document.documentElement.scrollLeft || document.documentElement.scrollTop)) {
		//IE6 standards compliant mode
		scrOfY = document.documentElement.scrollTop;
		scrOfX = document.documentElement.scrollLeft;
	}

	//alert(scrOfY);

	return [ scrOfX, scrOfY ];
}

function center1(id) {

	var myWidth = 0, myHeight = 0;
	if (typeof (window.innerWidth) == 'number') {
		//Non-IE
		myWidth = window.innerWidth;
		myHeight = window.innerHeight;
		// alert('None IE');

	} else if (document.documentElement
			&& (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
		//IE 6+ in 'standards compliant mode'
		myWidth = document.documentElement.clientWidth;
		myHeight = document.documentElement.clientHeight;
		// alert('IE 6+');

	} else if (document.body
			&& (document.body.clientWidth || document.body.clientHeight)) {
		//IE 4 compatible
		myWidth = document.body.clientWidth;
		myHeight = document.body.clientHeight;
		// alert('IE 4');

	}

	//window.alert( 'Width = ' + myWidth );
	// window.alert( 'Height = ' + myHeight )

	var scrollXY = getScrollXY();
	// alert(scrollXY[1]);

	el = document.getElementById(id);
	el.style.left = myWidth / 2 - 100 + 'px';
	el.style.top = scrollXY[1] + myHeight / 2 - 100 + 'px';
	// el.style.top = scrollXY[1] + 100 + 'px';

}

function setTopLeft(id) {
	el = document.getElementById(id);
	el.style.left = 0;
	el.style.top = 0;
}

function moneyConvert(id){
	 $("#"+id).keyup(function(){
			v = $(this).val();
			//alert(v);
			v = v.replace(/\D/g, "");
			v = v.replace(/\s/g, "");
			v = v.replace(/\t/g, "");
			v = v.replace(/^[0]+/g, "");

			v = v.replace(/(\d)(\d{15})$/, "$1,$2");
			v = v.replace(/(\d)(\d{12})$/, "$1,$2");
			v = v.replace(/(\d)(\d{9})$/, "$1,$2");
			v = v.replace(/(\d)(\d{6})$/, "$1,$2");
			v = v.replace(/(\d)(\d{3})$/, "$1,$2");
			
			$(this).val(v);
			//alert("xx "+v);
		});
}
