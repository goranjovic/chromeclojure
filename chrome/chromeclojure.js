
function sendMessage(response){
	var notification = webkitNotifications.createNotification(
	  'icon-small.png',
	  response.error ? 'Error' : 'Result',
	  response.result
	);
	notification.show();
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
			alert('Error!!1');
		}
		});
}

chrome.contextMenus.create({"title": "Eval as Clojure", "contexts" : ["selection"], "onclick": handleEvalClojureClick});




