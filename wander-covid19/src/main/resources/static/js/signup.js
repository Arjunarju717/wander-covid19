$(document).ready(function() {
	
	$('#userName').val("");
	$('#phone').val("");
	$('#emailAddress').val("");
	$('#password').val("");
	$('#confirmedPassword').val("");

	$("#signup").click(function(e) {
		var userName = $('#userName').val();
		var phone = $('#phone').val();
		var emailAddress = $('#emailAddress').val();
		var password = $('#password').val();
		var confirmedPassword = $('#confirmedPassword').val();
		if (userName == "") {
			alert("User Name is required");
			return false;
		}
		if (phone == "") {
			alert("Phone is required");
			return false;
		}
		if (emailAddress == "") {
			alert("Email is required");
			return false;
		}
		if (password == "") {
			alert("Password is required");
			return false;
		}
		if (confirmedPassword == "") {
			alert("Confirm Password is required");
			return false;
		}
		var userDTO = {
			userName : userName,
			phone : phone,
			emailAddress : emailAddress,
			password : password,
			confirmedPassword : confirmedPassword
		}
		$.ajax({
			url : '/user/create',
			type : 'post',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(userDTO),
			success : function(result) {
				alert(result.message);
				window.location.href = '/';
			},
			error : function(error) {
				alert(error.responseJSON.message);
			}
		});
	});
});