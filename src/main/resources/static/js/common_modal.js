function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showInvalidDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#invalidModal").modal();
}

function showValidDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#validModal").modal();
}

function showErrorModal(message) {
	showModalDialog("Error", message);
}

function showWarningModal(message) {
	showModalDialog("Warning", message);
}	