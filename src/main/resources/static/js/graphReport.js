var sprintInfo;
var parseStringToDate = function (dateString) {
	var day = parseInt(dateString.slice(-2));
	return new Date(dateString.substr(0, dateString.length - 2) + day);
};

var filterParamsSelectionType = function (updatedParams) {
	var selectionType = $('#selectionType').val();
	var filterArray;
	if (selectionType == "date") {
		filterArray = ["sheets", "startDate", "endDate"];
		for (var param in updatedParams) {
			if (filterArray.indexOf(param) == -1) {
				delete updatedParams[param];
			}
		}
	} else {
		filterArray = ["sheets", "startSprint", "endSprint"];
		for (var param in updatedParams) {
			if (filterArray.indexOf(param) == -1) {
				delete updatedParams[param];
			}
		}
		updatedParams['startDate'] = updatedParams['startSprint'];
		delete updatedParams['startSprint'];
		updatedParams['endDate'] = updatedParams['endSprint'];
		delete updatedParams['endSprint'];
	}
	return updatedParams;
};
var queryStringParams = function (params) {
	var updatedParams = {};
	var paramName;
	for (var i of params) {
		if (paramName != i.name) {
			paramName = i.name;
			var filteredparams = params.filter(function (element) {
				return element.name == i.name;
			});
			var paramValue = "";
			filteredparams.forEach(function (paramObject) {
				paramValue = !paramValue ? paramObject.value : paramValue + "," + paramObject.value;
			});
			updatedParams[paramName] = paramValue;
		}
	}
	updatedParams = filterParamsSelectionType(updatedParams);
	return updatedParams;
};
var showAndRequired = function (divNameList) {
	for (var divName of divNameList) {
		$("#" + divName + " input, #" + divName + " select").prop('required', true);
		$("#" + divName).show();
	}
};

var hideAndNotRequired = function (divNameList) {
	for (var divName of divNameList) {
		$("#" + divName + " input, #" + divName + " select").prop('required', false);
		$("#" + divName).hide();
	}
};

var updateForm = function (selectionType) {
	if (selectionType == "date") {
		hideAndNotRequired(["startPiDiv", "startSprintDiv", "endPiDiv", "endSprintDiv"]);
		showAndRequired(["startDateDiv", "endDateDiv"]);
	} else {
		hideAndNotRequired(["startDateDiv", "endDateDiv"]);
		showAndRequired(["startPiDiv", "startSprintDiv", "endPiDiv", "endSprintDiv"]);
	}
};

var now = new Date();
var day = ("0" + now.getDate()).slice(-2);
var month = ("0" + (now.getMonth() + 1)).slice(-2);
var today = now.getFullYear() + "-" + month + "-" + day;
var startDate = now.getFullYear() + "-" + month + "-" + "01";
$("#startDate").val(startDate);
$("#endDate").val(today);

$("#startDate").prop("max", today);
$("#endDate").prop("max", today);
var sheetsDropdown = $('#sheetsDropdown');
sheetsDropdown.empty();

updateForm($('#selectionType').val());

var successDistinctExcelNames = function (response) {
	for (var sheetName of response) {
		sheetsDropdown.append($('<option></option>').attr('value', sheetName).text(sheetName));
	}
};
$(document).off('change', '#selectionType').on('change', '#selectionType', function () {
	updateForm(this.value);
});

ajaxCall(uriFixedPath + "distinctExcelNames", "GET", null, successDistinctExcelNames, failure);

var successAllSprintInfo = function (response) {
	sprintInfo = response;
	for (var sprintRow of response) {
		var piName;
		if (sprintRow.pi != piName) {
			piName = sprintRow.pi;
			$('#startPiSelect').append($('<option></option>').attr('value', sprintRow.pi).text("PI " + sprintRow.pi));
			$('#endPiSelect').append($('<option></option>').attr('value', sprintRow.pi).text("PI " + sprintRow.pi));
		}
	}
	$("#startPiSelect").val($("#startPiSelect option")[1].value).change();
};

ajaxCall(uriFixedPath + "allSprintInfo", "GET", null, successAllSprintInfo, failure);

$(document).off('submit', 'form#graphForm').on("submit", "form#graphForm", function (event) {
	event.preventDefault();
	var graphSessionData = queryStringParams($(this).serializeArray());
	if (parseStringToDate(graphSessionData.startDate).getTime() <= parseStringToDate(graphSessionData.endDate).getTime()) {
		sessionStorage.setItem("paramsForGraph", JSON.stringify(graphSessionData));
		window.location.hash = "graph";
	} else {
		alert("Start Date can not be ahead of End Date.");
	}
});
$(document).off('change', '#selectAllSheets').on("change", "#selectAllSheets", function () {
	var checked = this.checked;
	$("#sheetsDropdown option").each(function () {
		$(this).prop("selected", checked);
	});
});

$(document).off('change', '#startPiSelect').on("change", "#startPiSelect", function () {
	$('#startSprint').empty();
	for (var sprintRow of sprintInfo) {
		if (sprintRow.pi == this.value) {
			$('#startSprint').append($('<option></option>').attr('value', sprintRow.startDate).text("Sprint " + sprintRow.sprint));
		}
	}
	$("#endPiSelect").val(this.value).change();
});

$(document).off('change', '#endPiSelect').on("change", "#endPiSelect", function () {
	$('#endSprint').empty();
	for (var sprintRow of sprintInfo) {
		if (sprintRow.pi == this.value) {
			$('#endSprint').append($('<option></option>').attr('value', sprintRow.endDate).text("Sprint " + sprintRow.sprint));
		}
	}
});