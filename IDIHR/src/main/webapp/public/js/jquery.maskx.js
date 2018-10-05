/**
 * @file jquery.maskx.js
 * @author Trần Đông Hải <trandonghai104@gmail.com>
 * @date 2018-09-27
 * 
 * @use jQuery('$input[name="cc"]').maskx({maskx: 'cc'});
 */
;
(function() {
	'use strict';
	/* global nomen:true, jQuery: false, setTimeout:false */
	var plugin = function(settings) {
		var $input, hiddenInput, maskTo, maskFrom;
		var _execmascara = function() {
			$input.value = maskTo($input.value);
			$(hiddenInput).val(maskFrom($input.value));
		};
		var _mascara = function(o1, o2, to, from) {
			$input = o1;
			hiddenInput = o2;
			maskTo = to;
			maskFrom = from;
			setTimeout(_execmascara, 1);
		};
		return this.each(function() {
			var inputName = $(this).prop("name");
			$(this).removeAttr("name");
			var inputHtml = "<input type='hidden' name='" + inputName
					+ "' value=''/>";
			var hiddenInput = $(inputHtml).insertBefore($(this));

			var $this = jQuery(this);
			var opts = jQuery.extend({}, jQuery.fn.maskx.defaults, settings);
			var maskxToFunc = jQuery.fn.maskx[opts.maskxTo];
			var maskxFromFunc = jQuery.fn.maskx[opts.maskxFrom];

			if (typeof maskxToFunc === 'function'
					&& typeof maskxFromFunc === 'function') {

				$this[0].type = 'tel';// $this.attr('type','tel');

				_mascara(this, hiddenInput, maskxToFunc, maskxFromFunc);

				$this.bind('keypress', function() {
					_mascara(this, hiddenInput, maskxToFunc, maskxFromFunc);
					$this.removeClass('is-empty');
				});
				$this.bind('blur', function() {
					if ($this.val() === '') {
						$this.addClass('is-empty');
					}
				});
			}
		});
	};
	jQuery.fn.maskx = plugin;

	plugin.defaults = {
		maskxTo : '',
		maskxFrom : '',
		classEmpty : 'is-empty'
	};

	plugin.ccTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/^(\d{4})(\d)/g, "$1 $2");
		v = v.replace(/^(\d{4})\s(\d{4})(\d)/g, "$1 $2 $3");
		v = v.replace(/^(\d{4})\s(\d{4})\s(\d{4})(\d)/g, "$1 $2 $3 $4");
		return v;
	};
	plugin.ccFrom = function(v) {
		return v;
	};

	plugin.cepTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/^(\d{5})(\d)/, "$1-$2");
		return v;
	};
	plugin.cepFrom = function(v) {
		return v;
	};

	plugin.cnpjTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/^(\d{2})(\d)/, "$1.$2");
		v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
		v = v.replace(/\.(\d{3})(\d)/, ".$1/$2");
		v = v.replace(/(\d{4})(\d)/, "$1-$2");
		return v;
	};
	plugin.cnpjFrom = function(v) {
		return v;
	};

	plugin.cpfTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d{3})(\d)/, "$1.$2");
		v = v.replace(/(\d{3})(\d)/, "$1.$2");
		v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
		return v;
	};
	plugin.cpfFrom = function(v) {
		return v;
	};

	plugin.dateBRTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d{2})(\d)/, "$1/$2");
		v = v.replace(/(\d{2})(\d)/, "$1/$2");
		v = v.replace(/(\d{2})(\d{2})$/, "$1$2");
		return v;
	};
	plugin.dateBRFrom = function(v) {
		return v;
	};

	plugin.hourTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d{2})(\d)/, "$1h$2");
		return v;
	};
	plugin.hourFrom = function(v) {
		return v;
	};

	plugin.moneyTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/\s/g, "");
		v = v.replace(/\t/g, "");
		v = v.replace(/^[0]+/g, "");

		v = v.replace(/(\d)(\d{20})$/, "$1.$2");
		v = v.replace(/(\d)(\d{17})$/, "$1.$2");
		v = v.replace(/(\d)(\d{14})$/, "$1.$2");
		v = v.replace(/(\d)(\d{11})$/, "$1.$2");
		v = v.replace(/(\d)(\d{8})$/, "$1.$2");
		v = v.replace(/(\d)(\d{5})$/, "$1.$2");
		v = v.replace(/(\d)(\d{2})$/, "$1,$2");
		return v;
	};
	plugin.moneyFrom = function(v) {
		pos = v.indexOf(',');
		v = v.substring(0, pos);
		v = v.replace(/\./g, "");
		return v;
	};

	plugin.simpleMoneyTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/\s/g, "");
		v = v.replace(/\t/g, "");
		v = v.replace(/^[0]+/g, "");

		v = v.replace(/(\d)(\d{30})$/, "$1,$2");
		v = v.replace(/(\d)(\d{27})$/, "$1,$2");
		v = v.replace(/(\d)(\d{24})$/, "$1,$2");
		v = v.replace(/(\d)(\d{21})$/, "$1,$2");
		v = v.replace(/(\d)(\d{18})$/, "$1,$2");
		v = v.replace(/(\d)(\d{15})$/, "$1,$2");
		v = v.replace(/(\d)(\d{12})$/, "$1,$2");
		v = v.replace(/(\d)(\d{9})$/, "$1,$2");
		v = v.replace(/(\d)(\d{6})$/, "$1,$2");
		v = v.replace(/(\d)(\d{3})$/, "$1,$2");
		return v;
	};
	plugin.simpleMoneyFrom = function(v) {
		v = v.replace(/,/g, "");
		return v;
	};

	plugin.phoneTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/^(\d{2})(\d)/g, "($1) $2");
		v = v.replace(/(\d)(\d{4})$/, "$1-$2");
		return v;
	};
	plugin.phoneFrom = function(v) {
		return v;
	};

	plugin.phoneUsaTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/^(\d{3})(\d)/g, "($1) $2");
		v = v.replace(/(\d)(\d{4})$/, "$1-$2");
		return v;
	};
	plugin.phoneUsaFrom = function(v) {
		return v;
	};

	plugin.rgTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d)(\d{7})$/, "$1.$2");
		v = v.replace(/(\d)(\d{4})$/, "$1.$2");
		v = v.replace(/(\d)(\d)$/, "$1-$2");
		return v;
	};
	plugin.rgFrom = function(v) {
		return v;
	};

	plugin.timeTo = function(v) {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d{1})(\d{2})(\d{2})/, "$1:$2.$3");
		return v;
	};
	plugin.timeFrom = function(v) {
		return v;
	};
}());
