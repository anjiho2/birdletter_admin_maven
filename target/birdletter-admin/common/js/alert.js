function jConfirm(title, message, callback) {
	$.confirm({
		'title'		: title,
		'message'	: message,
		'buttons'	: {
			'예'	: {
				'class'	: 'blue',
				'action': function(){
					$("#confirmOverlay").remove();
					callback();
				}
			},
			'아니오'	: {
				'class'	: 'gray',
				'action': function(){}	// Nothing to do in this case. You can as well omit the action property.
			}
		}
	});
}

function jAlert(message, focusId) {
	$.confirm({
		'title' 	: '알림',
		'message' 	: message,
		'buttons' 	: {
			'확인' : {
				'class' : 'blue',
				'action' : function(){
					if (focusId != "" || focusId != undefined) {
						$("#"+focusId).focus();
					}
				}
			}
		}
	});
}

function jAlert_Success(message, focusId) {
	$.confirm({
		'title' 	: '알림',
		'message' 	: message,
		'buttons' 	: {
			'확인' : {
				'class' : 'blue',
				'action' : function(){
					if (focusId != "" || focusId != undefined) {
						$("#"+focusId).focus();
					}
				}
			}
		}
	});
}
