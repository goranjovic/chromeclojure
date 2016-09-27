
function guid() {
	function s4() {
		return Math.floor((1 + Math.random()) * 0x10000)
			.toString(16)
			.substring(1);
	}
	return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
		s4() + '-' + s4() + s4() + s4();
}

function getToken(){
    var token = localStorage.getItem('token');
    if(!token){
        token = guid();
        localStorage.setItem('token', token);
    }
    return token;
}

var messageSenders = {

	'notification' : function(response){
        alert((response.error ? 'Error' : 'Result') + ':\n' + response.result);
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
		 url: 'http://chromeclojure.com/api/v1/eval',
		 type: 'POST',
		 dataType: 'json',
         data: {source: info.selectionText},
         headers : {'token' : getToken()},

		 success: function(response){
		 	sendMessage(response);
		 },
         error: function(){
			alert('There was an error while calling chromeclojure backend service. Please try again later or submit a bug report.');
		}
		});
}

chrome.contextMenus.create({"title": "Eval as Clojure", "contexts" : ["selection"], "onclick": handleEvalClojureClick});




