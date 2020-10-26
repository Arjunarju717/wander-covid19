$(document).ready(
		function() {
			$.ajax({
				  type: "GET",
				  url: "/covidapi/latestCovidData",
				  headers: {
				    "Authorization": "bearer " + localStorage.getItem('token'),
				  },
				  success: function (result){
					  var options = {
					  	title: {
					  		text: "India's Latest Covid-19 Data"
					  	},
					  	subtitles: [{
					  		text: "As of " + result.lastUpdatedAtApify
					  	}],
					  	animationEnabled: true,
					  	data: [{
					  		type: "pie",
					  		startAngle: 40,
					  		toolTipContent: "<b>{label}</b>: {y}",
					  		showInLegend: "true",
					  		legendText: "{label}",
					  		indexLabelFontSize: 16,
					  		indexLabel: "{label} - {y}",
					  		dataPoints: [
					  			{ y: result.activeCases, label: "Active Cases" },
					  			{ y: result.activeCasesNew, label: "Active Cases New" },
					  			{ y: result.recovered, label: "Recovered" },
					  			{ y: result.deaths, label: "Deaths" },
					  			{ y: result.deathsNew, label: "Deaths New" },
					  			{ y: result.previousDayTests, label: "Previous Day Tests" },
					  			{ y: result.recoveredNew, label: "Recovered New" },
					  			{ y: result.totalCases, label: "Total Cases" }
					  		]
					  	}]
					  };
					  $("#chartContainer").CanvasJSChart(options);

					  $.each(result.regionData, function(i, field) {
							$(".data-table tbody").append(
									"<tr><td>" + field.region + "</td><td>"
											+ field.newRecovered + "</td><td>"
											+ field.recovered + "</td><td>"
											+ field.newInfected + "</td><td>"
											+ field.totalInfected + "</td><td>"
											+ field.newDeceased + "</td><td>"
											+ field.deceased + "</td></tr>");
						});
				  }, error: function(error) {
					   alert("You have logged out..!! Please log in.");
			           window.location.href='/';
		          }
			});
			
			$(".eventTablefilter").keyup(function(e) {
			 var input, filter, table, tr, td, i, txtValue;
			  filter = this.value.toUpperCase();
			  table = document.getElementById("eventTable");
			  tr = table.getElementsByTagName("tr");
			  for (i = 0; i < tr.length; i++) {
			    td = tr[i].getElementsByTagName("td")[0];
			    if (td) {
			      txtValue = td.textContent || td.innerText;
			      if (txtValue.toUpperCase().indexOf(filter) > -1) {
			        tr[i].style.display = "";
			      } else {
			        tr[i].style.display = "none";
			      }
			    }
			  }
			});
			
			$("#signout").click(function(e) {
				$.ajax({
				  type: "DELETE",
				  url: "/user/logout",
				  dataType: 'json',
				  headers: {
				    "Authorization": "bearer " + localStorage.getItem('token'),
				  },
				  contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		          success: function (result){
					  localStorage.setItem('token', "");
					  window.location.href='/';
				  }, error: function(error) {
		         	  alert(error.responseJSON.error_description);
		          }
				});
			});
});
