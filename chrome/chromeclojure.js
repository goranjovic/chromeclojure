
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

var displayFns = {

    'notification' : function(response){

		var options = {
			type: 'basic',
			title: 'Eval as Clojure',
			message: (response.error ? 'Error' : 'Result') + ':\n' + response.result,
			iconUrl: 'icon-small.png'
		};

		chrome.notifications.create(options);
    },

	'alert' : function(response){
		alert((response.error ? 'Error' : 'Result') + ':\n' + response.result);
	}
};


function displayResult(response){
	var responseMethod = localStorage.getItem('responseMethod') || 'notification';
	var sender = displayFns[responseMethod];
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
		 	displayResult(response);
		 },
         error: function(){
			alert('There was an error while calling chromeclojure backend service. Please try again later or submit a bug report.');
		}
		});
}

chrome.contextMenus.create({"title": "Eval as Clojure", "contexts" : ["selection"], "onclick": handleEvalClojureClick});




