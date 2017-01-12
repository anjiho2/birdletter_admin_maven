jQuery.jQueryAlert = function (msg) {
    var $messageBox = $.parseHTML('<div id="alertBox"></div>');
    $("body").append($messageBox);

    $($messageBox).dialog({
        open: $($messageBox).append(msg),
        title: "알림",
        autoOpen: true,
        modal: true,
        buttons: {
            확인: function () {
                $("#alertBox").dialog("close");
            }
        }
    });
};

$.extend({ alert: function (message, title) {
	  $("<div></div>").dialog( {
	    buttons: { "확인": function () { $(this).dialog("close"); } },
	    close: function (event, ui) { $(this).remove(); },
	    resizable: false,
	    title: title,
	    modal: true
	  }).text(message);
	}
});

$.extend({ confirm: function (message, title, okFunction) {
	  $("<div></div>").dialog( {
		  buttons: { 
			  "확인": function () { okFunction; return true;}, 
			  "취소": function () { $(this).dialog("close"); }
		  }, 
	    close: function (event, ui) { $(this).remove(); },
	    resizable: false,
	    title: title,
	    modal: true
	  }).text(message);
	}
});

jQuery.jQueryConfirm = function (msg) {
	var $messageBox = $.parseHTML('<div id="alertBox"></div>');
    $("body").append($messageBox);

    $($messageBox).dialog({
        open: $($messageBox).append(msg),
        title: "알림",
        autoOpen: true,
        modal: true,
        buttons: {
            확인: function () {
                
            },
            취소: function () {
            	$("#alertBox").dialog("close");
            }
        }
    });
};
