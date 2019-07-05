$(document).ready(function(){
	$.ajax({
	url :'https://json2jsonp.com/?url=http://weather.livedoor.com/forecast/webservice/json/v1?city=130010' ,
		type: "GET" ,
		dataType: "jsonp" ,
		jsonpCallback: 'cb',
		success: function(data) {
			$("#weather-title").text(data.location.city + "の天気");
			for(var i = 0; i < data.forecasts.length; i++){
				forecast =data.forecasts[i];
				var $box = $("<div class='box'>");
				var $item = $("<dl>");
				//日付ラベル
				$("<dt>"+ forecast.dateLabel + "</dt>").appendTo($item);
				//日付
				$("<dt>"+ forecast.date + "</dt>").appendTo($item);
				//天気画像
				$('<dd><img src="' + forecast.image.url+'"alt="'
						+ forecast.image.title + '"/><dd>').appendTo($item);

				//天気（晴れ、曇り、など)
				$("<dd>" + forecast.telop + "</dd>").appendTo($item);
				//

				$item.appendTo($box);
				$box.appendTo($("#weather"));
			}

		},
		error: function() {
			console.log("エラー");
		}
	});
});
