var successRunTest = function (response) {
	$('#runTest').attr("disabled", false);
	$('#testingImg').css("display","none");
};	
$(document).off('submit', 'form#runTestForm').on("submit", "form#runTestForm", function (event) {
		event.preventDefault();
		$('#runTest').attr("disabled", true);
		$('#testingImg').css("display","block");
		var saveToDatabase = $("#SaveDatabase").is(":checked");
		var sendMail = $("#SendEmail").is(":checked");
	ajaxCall(uriFixedPath + "runtest", "POST", {sendMail, saveToDatabase }, successRunTest, failure);
});