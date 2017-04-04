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

//$(function() {
//    selectionChanged();
//});

// hook up the change event of the select box
//$('select').change(selectionChanged);
//
//// event handler for the change event
//
//function selectionChanged(e) {
//    console.log("select changed: "+$('select').val());
//    search($('select').val());
//}

var previousSearch = "";
   $('#search-input').on('keyup', function (e) {
         var val = $("#search-input").val();

         if(val != previousSearch){
            previousSearch = val;
            search();
         }
   });

var offset;

var sortMethod = "name";

//window.onload = function load(){
//    search(${'params'});
//}

function search(){
    console.log("search called in application.js");
    var key = $("#search-input").val();

    $.ajax(
        {
            url: "http://localhost:8080/user/sort",
            data: {
                "query": key,
                "asc": ascending,
                "offset": offset,
                "max": 10
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
}

function searchValue(){
    return $('#search-input').val();
}




