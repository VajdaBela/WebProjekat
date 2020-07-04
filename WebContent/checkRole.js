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