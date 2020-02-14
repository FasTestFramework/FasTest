var successRunTest = function (response) {
	$('#runTest').attr("disabled", false);
	$('#testingImg').css("display","none");
};	
$(document).off('submit', 'form#runTestForm').on("submit", "form#runTestForm", function (event) {
		event.preventDefault();
		$('#runTest').attr("disabled", true);
		$('#testingImg').css("display","block");
		var saveDatabase = $("#SaveDatabase").is(":checked");
		var sendEmail = $("#SendEmail").is(":checked");
		var queryParam = "?sendMail=" + sendEmail + "&saveToDatabase=" + saveDatabase;
	ajaxCall(uriFixedPath + "runtest"+ queryParam, "GET", null, successRunTest, failure);
});