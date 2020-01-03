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
		disEditableClass : "dis-editable",
		disRemovableClass : "dis-removable",
		beforeLoad : null,
		afterLoad : null,
		beforeSave : null,
		afterSave : null,
		beforeRemove : null,
		urlRemove : "",
		afterRemove : null,
		removable : true,
		editable : true
	};
	var params = $.extend(defaults, options);
	var table = this;

	// Create UI
	function createUI() {
		var newColHtml = ""
		if (params.removable && params.editable) {
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
			if (params.editable) {
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
			} else if (params.removable) {
				newColHtml = '<div class="btn-group pull-right">'
						+ '<button type="button" class="btn btn-sm btn-default bRemove" title="Remove">'
						+ '<span class="glyphicon glyphicon-trash" > </span>'
						+ '</button>';
			} else {
				newColHtml = '';
			}
		}

		var colEdicHtml = '<td>' + newColHtml + '</td>';
		var blankTd = "<td></td>";

		// Append buttons columns
		table.find('thead tr').append('<th name="buttons"></th>');
		table.find('tbody tr').has("." + params.cellClass).append(colEdicHtml);
		table.find('tbody tr').not("tbody tr").has("." + params.cellClass)
				.append(blankTd);

		table.find("." + params.cellClass).each(function() {
			var tr = $(this).parents('tr');
			if ($(this).hasClass(params.disEditableClass)) {
				$(tr).find('.bEdit').addClass('hide');
			}

			if ($(this).hasClass(params.disRemovableClass)) {
				$(tr).find('.bRemove').addClass('hide');
			}
		});

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
		var tr = $(this).parents("tr");
		var keyRow = $(tr).data();

		$(tr).find("." + params.cellClass).each(function() {
			try {
				content = $(this).html();
				var type = $(this).data("type");

				var div = '<div style="display: none;">' + content + '</div>';
				var text = "";
				if (type == "combobox") {
					text = createCombobox(keyRow, $(this));
				} else if (type == "textarea") {
					text = createTextArea(keyRow, content);
				} else {
					text = createTextField(keyRow, content);
				}
				$(this).html(div + text);
			} catch (e) {
				console.log("Create editable cell error", e);
			}
		});

		enableConfimButtons($(this).parent());
	}

	function rowRemove() {
		if (!confirm("Bạn muốn xoá dòng dữ liệu này không ?")) {
			return;
		}

		var tr = $(this).parents("tr");
		var keyRow = $(tr).data();
		var url = keyRow.removeUrl;
		var name = keyRow.name;

		var inputData = $.extend(true, {}, keyRow, null);
		delete inputData.saveUrl;
		delete inputData.removeUrl;
		delete inputData.name;

		try {
			inputData = params.beforeRemove[name].call(tr, inputData);
			inputData = JSON.stringify(inputData);
		} catch (e) {
			console.log("beforeRemove is not defined correctly", e);
		}
		console.log("rowRemove inputData", inputData);
		console.log("rowRemove url", url);

		$.ajax({
			type : "POST",
			url : url,
			data : inputData,
			contentType : "application/json",
			dataType : "json",
			success : function(data) {
				try {
					params.afterRemove[name].call(tr, data);
				} catch (e) {
					console.log("afterRemove is not defined correctly", e);
				}
			},
			error : function(data) {
				console.log("Error while remove row ", data);
			}
		});
	}

	function rowSave() {
		var tr = $(this).parents("tr");
		var keyRow = $(tr).data();
		var url = keyRow.saveUrl;
		var name = keyRow.name;

		var inputData = $.extend(true, {}, keyRow, null);
		delete inputData.saveUrl;
		delete inputData.removeUrl;
		delete inputData.name;

		$(tr).find("." + params.cellClass).each(function() {
			var cell = $(this);
			var cellData = $.extend(true, {}, $(this).data(), null);
			var field = cellData.field;
			var type = cellData.type;

			delete cellData.type;
			delete cellData.loadUrl;
			delete cellData.field;

			if (type == "combobox") {
				cellData.value = $(cell).find('select').val();
				cellData.label = $(cell).find('select option:selected').text();
			} else if (type == "textarea") {
				cellData.value = $(cell).find('textarea').val();
			} else {
				cellData.value = $(cell).find('input').val();
			}

			var cellDataObj = new Object();
			if (inputData[field] != null) {
				cellData[field] = inputData[field];
			}
			cellDataObj[field] = cellData;
			inputData = $.extend(true, {}, inputData, cellDataObj);
		});

		var sendingData = null;
		try {
			console.log("beforeSave for row ", name);
			sendingData = params.beforeSave[name].call(tr, inputData);
		} catch (e) {
			console.log("beforeSave is not defined exactly", e);
		}

		sendingData = JSON.stringify(sendingData);
		console.log("rowSave data (row & cell)", inputData);
		console.log("rowSave sending data", sendingData);
		console.log("rowSave url", url);

		$.ajax({
			type : "POST",
			url : url,
			data : sendingData,
			contentType : "application/json",
			dataType : "json",
			success : function(data) {
				console.log("return data", data);

				var sendingData = null;
				try {
					console.log("afterSave for row ", name);
					inputData = params.afterSave[name]
							.call(tr, inputData, data);
				} catch (e) {
					console.log("afterSave is not defined exactly", e);
				}

				console.log("Update row's key ... ");
				$.each(keyRow, function(key, value) {
					if (!$.isEmptyObject(inputData[key])
							&& !$.isEmptyObject(inputData[key].value)) {
						$(tr).data(key, inputData[key].value);
					}
				})

				console.log("Update each cell's value ...");
				$(tr).find("." + params.cellClass).each(function() {
					var cell = $(this);
					var cellData = $(cell).data();

					if (cellData.type == "combobox") {
						$(cell).html(inputData[cellData.field].label);
					} else {
						$(cell).html(inputData[cellData.field].value);
					}
				});

				// Back to normal
				disableConfimButtons($(tr));
			},
			error : function(data) {
				console.log("Saving row error", data);
				alert("Lỗi khi lưu thay đổi, xin hãy thử lại");
			}
		});
	}

	function rowCancel() {
		var tr = $(this).parents("tr");

		$(tr).find("." + params.cellClass).each(function() {
			try {
				var content = $(this).find('div').html();
				$(this).html(content);
			} catch (e) {
				// alert(e);
			}
		});

		// Back to normal
		disableConfimButtons($(tr));
	}

	function createCombobox(key, cell) {
		// Tạo combobox
		var result = '<select class="form-control input-sm"></select>';

		// Lấy dữ liệu cho combobox vừa tạo
		var cellDatas = $(cell).data();
		var inputData = $.extend(true, {}, key, cellDatas);
		var field = cellDatas.field;

		try {
			delete inputData.loadUrl;
			delete inputData.saveUrl;
			delete inputData.removeUrl;
			delete inputData.type;
			delete inputData.field;
			delete inputData.name;
			console.log("createCombobox data (row & cell)", inputData);

			inputData = params.beforeLoad[field].call(cell, inputData);
			inputData = JSON.stringify(inputData);
			console.log("createCombobox sending data", inputData);
		} catch (e) {
			console.log("createCombobox error", e);
		}

		$.ajax({
			type : "POST",
			url : cellDatas.loadUrl,
			data : inputData,
			contentType : "application/json",
			dataType : "json",
			success : function(data) {
				console.log("createCombobox result", data);
				var result = "";
				try {
					var list = params.afterLoad[field].call(cell, data);

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
					console.log("Error " + e);
				}

				$(cell).find("select").html(result);
			},
			error : function(data) {
				$(cell).find("select").html("");
			}
		});

		return result;
	}

	function createTextArea(key, content) {
		try {
			content = $.trim(content);
			var result = '<textarea class="form-control input-sm">' + content
					+ '</textarea>';
			return result;
		} catch (e) {
			return "";
		}
	}

	function createTextField(key, content) {
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
