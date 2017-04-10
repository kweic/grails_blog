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

var order = "asc"
var offset;
var sortMethod = "Name";
var previousSearch = "";

$('select').change(selectionChanged);

function selectionChanged(e) {
    console.log("select changed: "+$('select').val())
    sortMethod = $('select').val()
    console.log("order is: "+order)
    search()
}

//function swapOrderCheck(){
//    var selection = $('select').val()
//    if(selection === sortMethod){
//        flipOrder();
//    }else if(selection === "Last Post"){
//        order = "asc"
//    }
//    sortMethod = selection
//}

function flipOrder(){
    if(order === "asc"){
        order = "desc"
    }else{
        order = "asc"
    }
    console.log("order now: "+order)
}

$('#search-input').on('keyup', function (e) {
     var val = $("#search-input").val();

     if(val != previousSearch){
        previousSearch = val;
        search();
     }
});

function search(){
    var key = $("#search-input").val();

    $.ajax(
        {
            url: "http://localhost:8080/user/sort",
            data: {
                "query": key,
                "order": order,
                "sort": sortMethod,
                "offset": offset,
                "max": 10
            },

        "success": function(data){
               $('#user-results').html(data);
        }
    });
}

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




