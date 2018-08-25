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
		onSave : null, // Save new data to server
		onLoad : null
	// combobox: for a editable cell: load list of options
	};
	var params = $.extend(defaults, options);
	var table = this;

	// Create UI
	function createUI() {
		var newColHtml = '<div class="btn-group pull-right">'
				+ '<button type="button" class="btn btn-sm btn-default bEdit" title="Edit">'
				+ '<span class="glyphicon glyphicon-pencil" > </span>'
				+ '</button>'
				+ '<button type="button" class="btn btn-sm btn-default bSave" style="display:none;" title="Save">'
				+ '<span class="glyphicon glyphicon-ok" > </span>'
				+ '</button>'
				+ '<button type="button" class="btn btn-sm btn-default bCancel" style="display:none;" title="Cancel">'
				+ '<span class="glyphicon glyphicon-remove" > </span>'
				+ '</button>' + '</div>';

		var colEdicHtml = '<td style="width:90px;">' + newColHtml + '</td>';
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
		$(".bEdit").click(rowEdit);
		$(".bSave").click(rowSave);
		$(".bCancel").click(rowCancel);
	}

	function disableConfimButtons(tr) {
		tr.find('.bSave').hide();
		tr.find('.bCancel').hide();
		tr.find('.bEdit').show();
	}

	function enableConfimButtons(tr) {
		tr.find('.bSave').show();
		tr.find('.bCancel').show();
		tr.find('.bEdit').hide();
	}

	function rowEdit() {
		$(this).parents("tr").find("." + params.cellClass).each(function() {
			try {
				var content = $(this).html();
				var type = $(this).attr("type");

				var div = '<div style="display: none;">' + content + '</div>';
				var text = "";
				if (type == "combobox") {
					var param = $(this).attr("data");
					text = createCombobox(param);
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

	function rowSave() {
		try {
			$(this).parents("tr").find("." + params.cellClass).each(function() {
				var type = $(this).attr("type");

				var data = new Object();
				data.type = type;
				data.value = "";

				if (type == "combobox") {

				} else {
					data.value = $(this).find('input').val();
				}

				if (params.onSave != null) {
					// Save data to server
					params.onSave(data);
				}

				// Update data to html pages
				if (type == "combobox") {

				} else {
					$(this).html(data.value);
				}
			});

			// Back to normal
			disableConfimButtons($(this).parent());
		} catch (e) {
			alert("Error when save changes to server: " + e);
		}
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

	function createCombobox(param) {
		var dataList = null;
		try {
			// Load data from server
			dataList = params.onLoad(param);
		} catch (e) {
			// alert(e);
		}

		var result = '<select class="form-control input-sm">';
		try {
			// append data to combobox
			for (var i = 0; i < dataList.length; i++) {
				try {
					var option = '<option value="' + dataList[i].value + '">'
							+ dataList[i].label + '</option>';
					result += option;
				} catch (e) {
					// alert(e);
				}
			}
		} catch (e) {
			// alert(e);
		}
		result += '</select>';

		return result;
	}

	function createTextArea(content) {
		var result = '<textarea class="form-control input-sm">' + content
				+ '</textarea>';
		return result;
	}

	function createTextField(content) {
		var result = '<input class="form-control input-sm" value="' + content
				+ '"/>';
		return result;
	}

	createUI();
};
