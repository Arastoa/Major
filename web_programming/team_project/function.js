	function setIFrame(obj){
		if(obj.contentDocument){
			obj.height = obj.contentDocument.body.offsetHeight + 40;
			obj.width = 1026;
		} 
		else {
			obj.height = obj.contentWindow.document.body.offsetHeight;
			obj.width = 1026;
		}
	}
	
