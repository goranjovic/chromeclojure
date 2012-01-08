
var messageSenders = {

	'notification' : function(response){
		var notification = webkitNotifications.createNotification(
		  'icon-small.png',
		  response.error ? 'Error' : 'Result',
		  response.result
		);
		notification.show();
	},

	'alert' : function(response){
		alert((response.error ? 'Error' : 'Result') + ':\n' + response.result);
	}

};


function sendMessage(response){
	var responseMethod = localStorage['responseMethod'];
	if(responseMethod == undefined || responseMethod == null || responseMethod == ''){
		responseMethod = 'notification';
	}
	var sender = messageSenders[responseMethod];
	sender(response);
}

function handleEvalClojureClick(info, tab){


	 $.ajax({
		url: 'http://chromeclojure.com/eval',
		type: 'POST',
		dataType: 'json',
                data: {source: info.selectionText},
		success: function(response){
			sendMessage(response);
		},
                error: function(){
			alert('There was an error while calling chromeclojure backend service. Please try again later or submit a bug report.');
		}
		});
}

chrome.contextMenus.create({"title": "Eval as Clojure", "contexts" : ["selection"], "onclick": handleEvalClojureClick});




