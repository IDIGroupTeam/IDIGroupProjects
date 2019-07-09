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
		urlLoad : "",
		afterLoad : null,
		beforeRemove : null,
		urlRemove : "",
		afterRemove : null,
		beforeSave : null,
		urlSave : "",
		afterSave : null,
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
				console.log("Create editable cell", "error", e);
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
		var inputData = "";
		var cells = new Array();

		$(tr).find("." + params.cellClass).each(function() {
			var cell = $(this);
			var data = $(this).data();

			var value = "";
			if (data.type == "combobox") {
				value = $(cell).find('select').val();
			} else if (data.type == "textarea") {
				value = $(cell).find('textarea').val();
			} else {
				value = $(cell).find('input').val();
			}

			data.value = value;
			cells.push(data);
		});

		if (params.beforeRemove != null) {
			inputData = params.beforeRemove(keyRow, cells);
		}

		$.ajax({
			url : params.urlRemove,
			data : inputData,
			dataType : "json",
			type : "POST",
			success : function(data) {
				var obj = null;
				if (params.afterRemove != null) {
					obj = params.afterRemove(data);
				}
			},
			error : function(data) {
				console.log("Error while remove row " + data);
			}
		});
	}

	function rowSave() {
		var tr = $(this).parents("tr");
		var keyRow = $(tr).data();
		var inputData = "";
		var cells = new Array();

		$(tr).find("." + params.cellClass).each(function() {
			var cell = $(this);
			var data = $(this).data();

			var value = "";
			var label = null;
			if (data.type == "combobox") {
				value = $(cell).find('select').val();
				label = $(cell).find('select option:selected').text();
			} else if (data.type == "textarea") {
				value = $(cell).find('textarea').val();
			} else {
				value = $(cell).find('input').val();
			}

			data.value = value;
			data.label = label;
			cells.push(data);
		});

		if (params.beforeSave != null) {
			inputData = params.beforeSave(keyRow, cells);
		}
		console.log("rowSave", "inputData", inputData);

		$.ajax({
			url : params.urlSave,
			data : inputData,
			dataType : "json",
			type : "POST",
			success : function(data) {
				// Update row's key
				$.each(keyRow, function(key, value) {
					for (var i = 0; i < cells.length; i++) {
						if (cells[i].field == key) {
							$(tr).data(key, cells[i].value);
						}
					}
				})

				// Update each cell's value
				$(tr).find("." + params.cellClass).each(function() {
					var cell = $(this);
					var cellData = $(cell).data();

					for (var i = 0; i < cells.length; i++) {
						if (cells[i].field == cellData.field) {
							if (cellData.type == "combobox") {
								$(cell).html(cells[i].label);
							} else {
								$(cell).html(cells[i].value);
							}
						}
					}
				});

				if (params.afterSave != null) {
					obj = params.afterSave(tr, cells);
				}

				// Back to normal
				disableConfimButtons($(tr));
			},
			error : function(data) {
				console.log("Saving row", "error", data);
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
		var inputData = "";
		try {
			var cellDatas = $(cell).data();
			inputData = params.beforeLoad(key, cellDatas);
		} catch (e) {
			console.log("createCombobox", e);
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
