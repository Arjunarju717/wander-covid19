$(document).ready(function() {
	
	$('#userName').val(localStorage.getItem('userName'));
	$('#defaultPassword').val("");
	$('#newPassword').val("");
	$('#confirmedPassword').val("");

	$("#resetpassword").click(function(e) {
		var userName = $('#userName').val();
		var defaultPassword = $('#defaultPassword').val();
		var newPassword = $('#newPassword').val();
		var confirmPassword = $('#confirmedPassword').val();
		if (userName == "") {
			alert("User Name is required");
			return false;
		}
		if (defaultPassword == "") {
			alert("Default Pasword is required");
			return false;
		}
		if (newPassword == "") {
			alert("New Password is required");
			return false;
		}
		if (confirmPassword == "") {
			alert("Confirm Password is required");
			return false;
		}
		var resetPasswordDTO = {
			userName : userName,
			defaultPassword : defaultPassword,
			newPassword : newPassword,
			confirmPassword : confirmPassword
		}
		$.ajax({
			url : '/user/resetPassword',
			type : 'PATCH',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(resetPasswordDTO),
			success : function(result) {
				localStorage.setItem('userName', "");
				alert(result.message);
				window.location.href = '/';
			},
			error : function(error) {
				alert(error.responseJSON.message);
			}

		});

	});
});