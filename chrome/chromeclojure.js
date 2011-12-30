

function handleEvalClojureClick(info, tab){

	 $.ajax({
		url: 'http://chromeclojure.com/eval',
		type: 'POST',
		dataType: 'text',
                data: {source: info.selectionText},
		success: function(response){
			alert(response);
		},
                error: function(){
			alert('Error!!1');
		}
		});
}

chrome.contextMenus.create({"title": "Eval as Clojure", "contexts" : ["selection"], "onclick": handleEvalClojureClick});




