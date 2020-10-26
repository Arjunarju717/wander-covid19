$(document).ready(function() {

	$('#userName').val("");
	$('#password').val("");

	$("#signinpage").click(function(e) {
		window.location.href = '/sign-up.html';
	});

	$("#signin").click(function(e) {
		var clientName = 'web';
		var clientID = 'pin';
		var username = $('#username').val();
		var password = $('#password').val();
		if (username == "") {
			alert("User Name is required");
			return false;
		}
		if (password == "") {
			alert("Password is required");
			return false;
		}
		var userDTO = {
			grant_type : 'password',
			username : username,
			password : password
		}
		$.ajax({
			type : "POST",
			url : "/user/login",
			dataType : 'json',
			headers : {
				"Authorization" : "Basic " + btoa(clientName + ":" + clientID)
			},
			contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
			data : userDTO,
			success : function(result) {
				localStorage.setItem('token', result.access_token);
				window.location.href = '/covidupdates';
			},
			error : function(error) {
				alert(error.responseJSON.error_description);
			}
		});
	});
});