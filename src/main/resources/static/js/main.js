var uriFixedPath = "http://localhost:8085/";
function loadjsfile(filename){
        var fileref=document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src", "js/"+filename+".js");
        document.getElementById("main-content").appendChild(fileref);
}
var hashChangeProcess = function(){
	var page = window.location.hash.substring(1);
	var tabToActivate = $("ul#navList li a[href='"+window.location.hash+"']");
	if(tabToActivate && tabToActivate.length){
		$("ul#navList li a").removeClass("active");
		tabToActivate.addClass("active");
	}
  	if(page === ""){
	  window.location.hash = "#home";
  	}
  	$.get('pages/'+page+'.html', function(pageContent){
  		$('#main-content').html(pageContent);
  		loadjsfile(page);
  	});
};
var ajaxCall = function(urlToHit, type, payload, success, failure) {
    if (type == "GET") {
        $.ajax({
            type: type,
            contentType: "application/json",
            url: urlToHit,
            success: success,
            error: failure
        });
    } else {
        $.ajax({
            type: type,
            contentType: "application/json",
            url: urlToHit,
            data: JSON.stringify(payload),
            dataType: 'json',
            success: success,
            error: failure
        });
    }
};
var failure = function(response) {
    console.log(response);
};

$(document).ready(function () {
	hashChangeProcess();
	
    $(window).on('hashchange',function(){ 
    	hashChangeProcess();
  	});
    
    //set footer dynamically
    setInterval(function(){ 
		var mainContentMarginToSet = Math.round($("html").outerHeight(true) - $(".main-logo-box").outerHeight(true) -$("#navList").outerHeight(true) -$("footer").outerHeight(true) - $("#main-content").outerHeight(true));
		var mainContentmarginInitial = $("#main-content").css("margin-bottom");
		mainContentmarginInitial = parseInt(mainContentmarginInitial.substr(0, mainContentmarginInitial.length -2));
		mainContentMarginToSet = mainContentmarginInitial + mainContentMarginToSet >= 10 ? mainContentmarginInitial + mainContentMarginToSet: 10;
		$("#main-content").css("margin-bottom", mainContentMarginToSet); 
		}, 10);
});
