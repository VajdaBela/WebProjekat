function logout() {
	$.ajax({
		url: "rest/login/off",
		type: "GET",
		complete: function(data) {
			console.log(data);
			window.location.replace("SiteTemplate.html");
		}
	});
}
function makeNavElem(word, link) {
	return $("<li></li>").addClass("nav-item").append(
			$("<a></a>").addClass("nav-link").attr("href", link).text(word));
}
$(document).ready(function () {
	switch (uloga) {
	case "KORISNIK":
		$("#levo").append(makeNavElem("Virtualne Masine", "#"));
		$("#levo").append(makeNavElem("Diskovi", "#"));
		$("#levo").append(makeNavElem("Profil", "#"));
		$("#desno").append(makeNavElem("Logout", "javascript:logout()"));
		break;
	case "ADMIN":
		$("#levo").append(makeNavElem("Korisnici", "#"));
		$("#levo").append(makeNavElem("Virtualne Masine", "#"));
		$("#levo").append(makeNavElem("Diskovi", "#"));
		$("#levo").append(makeNavElem("Organizacija", "#"));
		$("#levo").append(makeNavElem("Profil", "#"));
		$("#levo").append(makeNavElem("Mesecna Cena", "#"));
		$("#desno").append(makeNavElem("Logout", "javascript:logout()"));
		break;
	case "SUPER_ADMIN":
		$("#levo").append(makeNavElem("Organizacije", "#"));
		$("#levo").append(makeNavElem("Korisnici", "#"));
		$("#levo").append(makeNavElem("Virtualne Masine", "#"));
		$("#levo").append(makeNavElem("Diskovi", "#"));
		$("#levo").append(makeNavElem("Kategorije", "#"));
		$("#levo").append(makeNavElem("Profil", "#"));
		$("#desno").append(makeNavElem("Logout", "javascript:logout()"));
		break;
	default:

	}
});