$(document).ready(function() {
	$('#userName').val("");
	$("#forgotpassword").click(function(e) {
		var userName = $('#username').val();
		if (userName == "") {
			alert("User Name is required");
			return false;
		}
		$.ajax({
			type : "PATCH",
			url : "/user/forgotPassword/" + userName,
			dataType : 'json',
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			success : function(result) {
				localStorage.setItem('userName', userName);
				alert("Default password has been sent to your mail.");
				window.location.href = '/resetPassword';
			},
			error : function(error) {
				alert(error.responseJSON.message);
			}
		});
	});
});