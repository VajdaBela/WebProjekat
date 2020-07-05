uloga = null;
$.ajax({
	url: "/WebProjekat/rest/login",
	async: false,
	type: "GET",
	complete: function(data) {
		uloga = data.responseJSON;
		console.log(uloga);
	}
});
function checkRedirect(uloge, destination) {
	for(var i = 0; i < uloge.length; ++i) {
		if(uloga === uloge[i]) {
			return;
		}
	}
	window.location.replace(destination);
}
function getAllParams() {
    var params = {};
    var splits = window.location.href.split("?")
    if(splits.length <= 1){
        return params
    }
    var allParams = window.location.href.split("?")[1];
    var nameValues = allParams.split("&");
    for(var i = 0; i < nameValues.length; i++) {
        var splited = nameValues[i].split("=")
        params[splited[0]] = splited[1];
    }
    return params;
}