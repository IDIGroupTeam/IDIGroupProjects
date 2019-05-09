/**
 * Written by Tran Dong Hai, Ha Noi, Dec 26, 2018
 * 
 * Input table must satified: all trs have id, all trs are sorted with correct
 * order, first column is tree column.
 */
$.fn.treeTable = function(options) {
	// Initiate options
	var defaults = {
		expandAll : true
	};
	var params = $.extend(defaults, options);
	var table = this;
	var trs = null;

	function init() {
		console.log("Initiate tree table ...");

		loadTable();
	}

	function loadTable() {
		console.log("Load tree table ...");

		// Initiate array of tr's id
		trs = new Array();
		trsStr = "";
		var i = 0;
		$(table).find("tbody tr").each(function() {
			var tr = $(this);
			var trId = $(tr).attr("id");

			var trIds = trId.split("_");
			var deep = trIds.length - 1;
			if (deep > 0 && !params.expandAll) {
				$(tr).addClass('hidden');
			}

			trs[i] = trId;
			i++;
		});

		$(table).find("tbody tr").each(function() {
			var tr = $(this);
			var trId = $(tr).attr("id")

			var childIds = getDirectChilds(trId);
			var prefix = "";
			if (childIds != null && childIds != "") {
				prefix = createExpandCollapseIcon(childIds);
			}

			// first column is tree column.
			var firstTd = $(this).find("td:nth-child(1)");
			var trIds = trId.split("_");
			var deep = trIds.length - 1;
			createDeep(firstTd, deep);
			firstTd.html(prefix + firstTd.html());
		});

		registerEvent();
	}

	function getDirectChilds(trId) {
		var childIds = null;
		if (trs != null && trs.length > 0) {
			childIds = "";
			for (var i = 0; i < trs.length; i++) {
				var pos = trs[i].indexOf(trId + "_");
				if (pos > -1) {
					var subStr = trs[i].substring(pos + trId.length + 1,
							trs[i].length);
					if (subStr.indexOf("_") == -1) {
						childIds += trs[i] + ",";
					}
				}
			}
			if (childIds.length > 0) {
				childIds = childIds.substring(0, childIds.length - 1);
			}
		}

		return childIds;
	}

	function createDeep(col, deep) {
		if (deep > 0) {
			$(col).addClass("level-" + deep);
		}
	}

	function createExpandCollapseIcon(ids) {
		var collapse = "<button type='button' class='collapse-button' data-target='"
				+ ids
				+ "'><i class='glyphicon glyphicon-minus-sign text-info'></i></button>";

		if (!params.expandAll) {
			collapse = "<button type='button' class='collapse-button' data-target='"
					+ ids
					+ "'><i class='glyphicon glyphicon-plus-sign text-info'></i></button>";
		}

		return collapse;
	}

	function registerEvent() {
		console.log("Register events ...");
		$('.collapse-button').click(function() {
			showHide($(this).parent().parent());
			return false;
		});
	}

	function showHide(row) {
		if (row == null) {
			return;
		}

		var isExpand = $(row).find('.collapse-button i').hasClass(
				'glyphicon-minus-sign');

		if (isExpand) {
			// Đang mở, ấn sẽ đóng hết các con
			hide(row, false);
		} else {
			// Đang đóng, khi mở sẽ mở cấp 1, cấp 2 trở đi vẫn đóng
			show(row, true);
		}
	}

	function show(row, showChild) {
		if (row == null) {
			return;
		}

		var id = $(row).attr("id");

		if (showChild) {
			// show all the first level childs
			var target = $(row).find('.collapse-button').data('target');
			var childIds = target.split(",");
			if (childIds != null && childIds.length > 0) {
				for (var i = 0; i < childIds.length; i++) {
					show($("#" + childIds[i]), false);
				}

				// change icon to sub, after show all its first childs
				var button = $(row).find('.collapse-button i');
				if (!$(button).hasClass('glyphicon-minus-sign')) {
					$(button).toggleClass('glyphicon-minus-sign').toggleClass(
							'glyphicon-plus-sign');
				}
			}
		}

		// show itseft
		if ($(row).hasClass('hidden')) {
			$(row).toggleClass('hidden');
		}
	}

	function hide(row, itseft) {
		if (row == null) {
			return;
		}

		// hide all childs
		var id = $(row).attr("id");
		$("tr[id^='" + id + "_'").each(function() {
			hide($(this), true);
		});

		// Change icon to plus after hide all its childs
		var button = $(row).find('.collapse-button i');
		if (!$(button).hasClass('glyphicon-plus-sign')) {
			$(button).toggleClass('glyphicon-minus-sign').toggleClass(
					'glyphicon-plus-sign');
		}

		// Hide itseft
		if (itseft) {
			if (!$(row).hasClass('hidden')) {
				$(row).toggleClass('hidden');
			}
		}
	}

	init();
}