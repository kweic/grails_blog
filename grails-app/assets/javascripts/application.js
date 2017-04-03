// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.2.0.min
//= require bootstrap
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
    (function($) {
        $(document).ajaxStart(function() {
            $('#spinner').fadeIn();
        }).ajaxStop(function() {
            $('#spinner').fadeOut();
        });
    })(jQuery);
}

$(function() {
    selectionChanged();
});

// hook up the change event of the select box
$('select').change(selectionChanged);

// event handler for the change event

function selectionChanged(e) {
    console.log("select changed: "+$('select').val());
    search($('select').val());
}


var ascending = true;

var sortMethod = "name";

function checkOrderChange(chosenSort, previousSort){
    if(chosenSort === previousSort){
        console.log("same select, changed from asc to desc")
        ascending = !ascending;
    }
}



function search(sortMethod){
    var key = $("#search-input").val();
    console.log("doing sort type: "+sortMethod+" with term: "+key);
    $("#search_results").empty();
    $.ajax(
        {
            "url": "http://localhost:8080/user/sort",
            "data": {
                "query": key,
                "sort": sortMethod,
                "asc": ascending
            },

        "success": function(data){
               $('#user-results').html(data);
        }
    });
}



var ascending = true;
var context = "testing";

var filter = {
	init: function(){

	console.log("init function called");

		$(document).on("click", "#search", function(){
			console.log("search click");
		})
		$(document).on("click", "#name-heading", function(){

		})

}




