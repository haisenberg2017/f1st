document
		.write("<script type='text/javascript' src='/js/page/util/md5.js'><\/script>");
// js加密
var getSignParams = function(params) {
	var params = params;
	var paramsList = [];
	var sigeStr = '';
	delete params.sign;
	for ( var i in params) {
		paramsList.push(i);
	}
	paramsList.sort(function(l, r) {
		return l > r ? 1 : -1;
	});
	for (var j = 0; j < paramsList.length; j++) {
		sigeStr += paramsList[j] + '=' + params[paramsList[j]] + '&';
	}
	if (sigeStr == '') {
		sigeStr += '&';
	}
	sigeStr = MD5(sigeStr + 'secret=z3ydBD2-bC84b9@6$3f2C_d08o9-6969C7eB');
	params.sign = sigeStr;
	return params;
};
// cookie操作

// cookie 封装 获取 cookie
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

// 删除cookie
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

// 设置cookie 以及过期时间

function setCookie(name, value, time) {
	var strsec = getsec(time);
	console.log("过期时间为--->" + strsec);
	var exp = new Date();
	exp.setTime(exp.getTime() + strsec * 1);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}

// 获取设置过期时间
function getsec(str) {
	alert(str);
	var str1 = str.substring(1, str.length) * 1;
	var str2 = str.substring(0, 1);
	if (str2 == "s") {
		return str1 * 1000;
	} else if (str2 == "h") {
		return str1 * 60 * 60 * 1000;
	} else if (str2 == "d") {
		return str1 * 24 * 60 * 60 * 1000;
	}
}