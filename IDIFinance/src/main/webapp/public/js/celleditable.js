/*
CellEditable
 @description  Javascript library to make a HTML table cell editable, using Jquery/Bootstrap. 
 			   Type of control: textfield, textarea, combobox for a cell editable.
 @version 1.1
 @autor Tran Dong Hai
 */

$.fn.cellEditable = function(options) {
	// Initiate options
	var defaults = {
		cellClass : "cell-editable",
		beforeLoad : null,
		urlLoad : "",
		afterLoad : null,
		beforeRemove : null,
		urlRemove : "",
		afterRemove : null,
		beforeSave : null,
		urlSave : "",
		afterSave : null,
		removable : true
	};
	var params = $.extend(defaults, options);
	var table = this;

	// Create UI
	function createUI() {
		var newColHtml = ""
		if (params.removable) {
			newColHtml = '<div class="btn-group pull-right">'
					+ '<button type="button" class="btn btn-sm btn-default bEdit" title="Edit">'
					+ '<span class="glyphicon glyphicon-pencil" > </span>'
					+ '</button>'
					+ '<button type="button" class="btn btn-sm btn-default bRemove" title="Remove">'
					+ '<span class="glyphicon glyphicon-trash" > </span>'
					+ '</button>'
					+ '<button type="button" class="btn btn-sm btn-default bSave" style="display:none;" title="Save">'
					+ '<span class="glyphicon glyphicon-ok" > </span>'
					+ '</button>'
					+ '<button type="button" class="btn btn-sm btn-default bCancel" style="display:none;" title="Cancel">'
					+ '<span class="glyphicon glyphicon-remove" > </span>'
					+ '</button>' + '</div>';
		} else {
			newColHtml = '<div class="btn-group pull-right">'
					+ '<button type="button" class="btn btn-sm btn-default bEdit" title="Edit">'
					+ '<span class="glyphicon glyphicon-pencil" > </span>'
					+ '</button>'
					+ '<button type="button" class="btn btn-sm btn-default bSave" style="display:none;" title="Save">'
					+ '<span class="glyphicon glyphicon-ok" > </span>'
					+ '</button>'
					+ '<button type="button" class="btn btn-sm btn-default bCancel" style="display:none;" title="Cancel">'
					+ '<span class="glyphicon glyphicon-remove" > </span>'
					+ '</button>' + '</div>';
		}

		var colEdicHtml = '<td style="width:85px;">' + newColHtml + '</td>';
		var blankTd = "<td></td>";

		// Append buttons columns
		table.find('thead tr').append('<th name="buttons"></th>');
		table.find('tbody tr').has("." + params.cellClass).append(colEdicHtml);
		table.find('tbody tr').not("tbody tr").has("." + params.cellClass)
				.append(blankTd);

		registryEvent();
	}

	// Registry event for all elements
	function registryEvent() {
		try {
			$(".bEdit").click(rowEdit);
			$(".bRemove").click(rowRemove);
			$(".bSave").click(rowSave);
			$(".bCancel").click(rowCancel);
		} catch (e) {
		}
	}

	function disableConfimButtons(tr) {
		try {
			tr.find('.bSave').hide();
			tr.find('.bCancel').hide();
			tr.find('.bEdit').show();
			tr.find('.bRemove').show();
		} catch (e) {
			// alert(e);
		}
	}

	function enableConfimButtons(tr) {
		try {
			tr.find('.bSave').show();
			tr.find('.bCancel').show();
			tr.find('.bEdit').hide();
			tr.find('.bRemove').hide();
		} catch (e) {
		}
	}

	function rowEdit() {
		$(this).parents("tr").find("." + params.cellClass).each(function() {
			try {
				var content = $(this).html();
				var type = $(this).attr("type");

				var div = '<div style="display: none;">' + content + '</div>';
				var text = "";
				if (type == "combobox") {
					text = createCombobox($(this));
				} else if (type == "textarea") {
					text = createTextArea(content);
				} else {
					text = createTextField(content);
				}
				$(this).html(div + text);
			} catch (e) {
				// alert(e);
			}
		});

		enableConfimButtons($(this).parent());
	}

	function rowRemove() {
		if (!confirm("Bạn muốn xoá chỉ tiêu CĐKT này không ?")) {
			return;
		}

		$(this).parents("tr").find("." + params.cellClass).each(function() {
			var cell = $(this);
			var type = $(this).attr("type");

			var inputData = "";
			var key = $(cell).attr("data");

			if (params.beforeRemove != null) {
				inputData = params.beforeRemove(key);
			}

			$.ajax({
				url : params.urlRemove,
				data : inputData,
				dataType : "json",
				type : "POST",
				success : function(data) {
					// Loading ...

					// Save data to server ...
					var obj = null;
					if (params.afterRemove != null) {
						obj = params.afterRemove(type, cell, data);
					} else {

					}

					// Back to normal
					disableConfimButtons($(cell).parent());
				},
				error : function(data) {
					alert("Có lỗi " + data);
				}
			});
		});

		// Back to normal
		disableConfimButtons($(this).parent());
	}

	function rowSave() {
		$(this).parents("tr").find("." + params.cellClass).each(function() {
			var cell = $(this);
			var type = $(this).attr("type");

			var inputData = "";
			var key = $(cell).attr("data");
			var value = "";
			if (type == "combobox") {
				value = $(cell).find('select').val();
			} else {
				value = $(cell).find('input').val();
			}

			if (params.beforeSave != null) {
				inputData = params.beforeSave(key, value);
			}

			$.ajax({
				url : params.urlSave,
				data : inputData,
				dataType : "json",
				type : "POST",
				success : function(data) {
					// Loading ...

					// Save data to server ...
					var obj = null;
					if (params.afterSave != null) {
						obj = params.afterSave(type, cell, data);
					} else {
						if (type == "combobox") {
							// do nothing
						} else {
							$(cell).html(value);
						}
					}

					// Back to normal
					disableConfimButtons($(cell).parent());
				},
				error : function(data) {
					alert("Dữ liệu có thể trùng lặp " + data);
				}
			});
		});
	}

	function rowCancel() {
		$(this).parents("tr").find("." + params.cellClass).each(function() {
			try {
				var content = $(this).find('div').html();
				$(this).html(content);
			} catch (e) {
				// alert(e);
			}
		});

		// Back to normal
		disableConfimButtons($(this).parent());
	}

	function createCombobox(cell) {
		// Tạo combobox
		var result = '<select class="form-control input-sm"></select>';

		// Lấy dữ liệu cho combobox vừa tạo
		var inputData = "";
		try {
			var param = $(cell).attr("data");
			inputData = params.beforeLoad(param);
		} catch (e) {
		}

		$.ajax({
			url : params.urlLoad,
			data : inputData,
			dataType : "json",
			type : "POST",
			success : function(data) {
				var result = "";
				try {
					var list = params.afterLoad(data);

					// append data to combobox
					for (var i = 0; i < list.length; i++) {
						try {
							var option = '<option value="' + list[i].value
									+ '">' + list[i].label + '</option>';
							result += option;
						} catch (e) {
							// alert(e);
						}
					}
				} catch (e) {
				}

				$(cell).find("select").html(result);
			},
			error : function(data) {
				$(cell).find("select").html("");
			}
		});

		return result;
	}

	function createTextArea(content) {
		try {
			content = $.trim(content);
			var result = '<textarea class="form-control input-sm">' + content
					+ '</textarea>';
			return result;
		} catch (e) {
			return "";
		}

	}

	function createTextField(content) {
		try {
			content = $.trim(content);
			var result = '<input class="form-control input-sm" value="'
					+ content + '"/>';
			return result;
		} catch (e) {
			return "";
		}
	}

	createUI();
};
