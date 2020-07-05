function logout() {
	$.ajax({
		url: "/WebProjekat/rest/login/off",
		type: "GET",
		complete: function(data) {
			console.log(data);
			window.location.replace("/WebProjekat/login.html");
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
		$("#levo").append(makeNavElem("Virtualne Masine", "/WebProjekat/liste/VirtualneMasine.html"));
		$("#levo").append(makeNavElem("Diskovi", "/WebProjekat/liste/Diskovi.html"));
		$("#levo").append(makeNavElem("Profil", "/WebProjekat/Izmena/KorisnikSebe.html"));
		$("#desno").append(makeNavElem("Logout", "javascript:logout()"));
		break;
	case "ADMIN":
		$("#levo").append(makeNavElem("Korisnici", "/WebProjekat/liste/Korisnici.html"));
		$("#levo").append(makeNavElem("Virtualne Masine", "/WebProjekat/liste/VirtualneMasine.html"));
		$("#levo").append(makeNavElem("Diskovi", "/WebProjekat/liste/Diskovi.html"));
		$("#levo").append(makeNavElem("Organizacija", "/WebProjekat/Pogled/Organizacija.html?my=true"));
		$("#levo").append(makeNavElem("Profil", "/WebProjekat/Izmena/KorisnikSebe.html"));
		$("#levo").append(makeNavElem("Mesecna Cena", "#"));
		$("#desno").append(makeNavElem("Logout", "javascript:logout()"));
		break;
	case "SUPER_ADMIN":
		$("#levo").append(makeNavElem("Organizacije", "/WebProjekat/liste/Organizacije.html"));
		$("#levo").append(makeNavElem("Korisnici", "/WebProjekat/liste/Korisnici.html"));
		$("#levo").append(makeNavElem("Virtualne Masine", "/WebProjekat/liste/VirtualneMasine.html"));
		$("#levo").append(makeNavElem("Diskovi", "/WebProjekat/liste/Diskovi.html"));
		$("#levo").append(makeNavElem("Kategorije", "/WebProjekat/liste/Kategorije.html"));
		$("#levo").append(makeNavElem("Profil", "/WebProjekat/Izmena/KorisnikSebe.html"));
		$("#desno").append(makeNavElem("Logout", "javascript:logout()"));
		break;
	default:

	}
});