$(document).ready(function(){
	$("#input-form").submit(function(e){
		e.preventDefault();
		var user = $("#user").val();
		$.ajax({
			type: "get",
			dataType : "json" ,
			url : "http://geolocdb.appspot.com/api" ,
			data :{
				action : "greet",
				name: user
			} ,
			success : function(res) {
				$("#output-text").text(res.message);
			}

		});

	});

});