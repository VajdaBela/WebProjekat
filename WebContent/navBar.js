function logout() {
	$.ajax({
		url: "rest/login/off",
		type: "POST",
		complete: function(data) {
			//responseJSON: KORISNIK
			window.location.replace("login.html");
		}
	});
}

function makeNavElem(word, link) {
	return $("<li></li>").addClass("nav-item").append($("<a></a>").addClass("nav-link").attr("href", link).text(word));
}

function setKorisnikNavBar() {
	$("#desno").append(makeNavElem("Logout", "javascript:logout()"));
	$("#levo").append(makeNavElem("Korisnik", "#"));
}

function setNavBar(type) {
	switch(type) {
	case "KORISNIK":
		setKorisnikNavBar();
		break;
	default:
		
	}
}

function potentialRedirect(type) {
	
	if(type === "NEULOGOVAN") {
		window.location.replace("login.html");
	}
}

$.ajax({
	url: "rest/login/on",
	type: "POST",
	complete: function(data) {
		//responseJSON: KORISNIK
		potentialRedirect(data.responseJSON);
		setNavBar(data.responseJSON);
	}
});


