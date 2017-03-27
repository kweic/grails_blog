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


var comments = {
	init: function(){
	    console.log("comments init called")

		var sortMethod = "name";

		$(document).on("click", "#comment-button", function(){
		    console.log("clicked comment")
		    comments.printComments();
		})
	},

	printComments: function(){
	console.log("in printcomments function");
		$("#comments").empty();
		$.ajax(
			{
				"url": "${g.createLink(controller:'comment',action:'test')}",
				"data": {
                  type: "post",
                    dataType: 'json',
				},

			"success": function(responseData){
			console.log("success in ajax")
					$("#comments").append("test");
			}
		});
	}
}