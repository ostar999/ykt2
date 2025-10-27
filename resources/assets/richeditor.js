'use strict';
'use struct';

function HashMap(){
     /** Map 大小 **/
     var size = 0;
     /** 对象 **/
     var entry = new Object();

     /** 存 **/
     this.put = function (key , value)
     {
         if(!this.containsKey(key))
         {
             size ++ ;
         }
         entry[key] = value;
     }

     /** 取 **/
     this.get = function (key)
     {
         if( this.containsKey(key) )
         {
             return entry[key];
         }
         else
         {
             return null;
         }
     }

     /** 删除 **/
     this.remove = function ( key )
     {
         if( delete entry[key] )
         {
             size --;
         }
     }

     /** 是否包含 Key **/
     this.containsKey = function ( key )
     {
         return (key in entry);
     }

     /** 是否包含 Value **/
     this.containsValue = function ( value )
     {
         for(var prop in entry)
         {
             if(entry[prop] == value)
             {
                 return true;
             }
         }
         return false;
     }

     /** 所有 Value **/
     this.values = function ()
     {
         var values = new Array(size);
         for(var prop in entry)
         {
             values.push(entry[prop]);
         }
         return values;
     }

     /** 所有 Key **/
     this.keys = function ()
     {
         var keys = new Array(size);
         for(var prop in entry)
         {
             keys.push(prop);
         }
         return keys;
     }

     /** Map Size **/
     this.size = function ()
     {
         return size;
     }
 }

var RE = {
	currentRange: {
		startContainer: null,
		startOffset: 0,
		endContainer: null,
		endOffset: 0
	},
	cache: {
		editor: null,
		title: null,
		currentLink: null,
		line: null,
		//------- 删除超链接功能代码开始--------
		isDeleteLink: false,
		links: null
		//------- 删除超链接功能代码结束--------
	},
	commandSet: ['bold', 'italic', 'strikethrough', 'redo', 'undo'],
	schemeCache: {
		FOCUS_SCHEME: 'focus://',
		CHANGE_SCHEME: 'change://',
		STATE_SCHEME: 'state://',
		CALLBACK_SCHEME: 'callback://',
		IMAGE_SCHEME: 'image://'
	},
	setting: {
		screenWidth: 0,
		margin: 20
	},
	imageCache: new HashMap(),
	init: function init() {
		//初始化内部变量
		var _self = this;
		_self.initCache();
		_self.initSetting();
		_self.bind();
		_self.focus();
	},
	bind: function bind() {
		var _self = this;

		var _self$schemeCache = _self.schemeCache,
		    FOCUS_SCHEME = _self$schemeCache.FOCUS_SCHEME,
		    STATE_SCHEME = _self$schemeCache.STATE_SCHEME,
		    CALLBACK_SCHEME = _self$schemeCache.CALLBACK_SCHEME;


		document.addEventListener('selectionchange', _self.saveRange, false);

		_self.cache.editor.addEventListener('focus', function () {
			AndroidInterface.setViewEnabled(true);
		}, false);

//		_self.cache.title.addEventListener('blur', function () {
//			AndroidInterface.setViewEnabled(false);
//		}, false);

		_self.cache.editor.addEventListener('blur', function () {
		    AndroidInterface.setViewEnabled(false);
			_self.saveRange();
		}, false);

		_self.cache.editor.addEventListener('click', function (evt) {
			_self.saveRange();
			_self.getEditItem(evt);

			//------- 删除超链接功能代码开始--------
			_self.insertHtml('')
			_self.cache.isDeleteLink = false
			_self.cache.links.forEach(item => {
				item.style.backgroundColor = '#fff'
			})
			//------- 删除超链接功能代码结束--------
		}, false);

		_self.cache.editor.addEventListener('keyup', function (evt) {
			if (evt.which == 37 || evt.which == 39 || evt.which == 13 || evt.which == 8) {
				_self.getEditItem(evt);
			}
		}, false);
//		_self.cache.title.addEventListener('input',function(){
//            AndroidInterface.setTitleHtml(_self.getTitle());
//		},false),


		//------- 删除超链接功能代码开始--------
		_self.cache.editor.addEventListener('beforeinput', function (event) {
			// 检查输入类型是否为删除（delete）
			if (event.inputType === 'deleteContentBackward' || event.inputType === 'deleteContentForward') {
				// 检查是否选中了超链接
				const selection = window.getSelection();
				if (selection.rangeCount > 0) {
					const range = selection.getRangeAt(0);
					// 如果选中的范围内有超链接，阻止删除操作
					if (range.commonAncestorContainer.parentElement.nodeName === 'A') {
						if(!_self.cache.isDeleteLink) {
							range.commonAncestorContainer.parentElement.style.backgroundColor = '#ccc'
							_self.cache.isDeleteLink = true

						}else{
							_self.cache.editor.removeChild(selection.anchorNode.parentElement);
							_self.cache.isDeleteLink = false

						}
						event.preventDefault();
						_self.insertHtml('')
					}
				}
			}

		}, false);
		//------- 删除超链接功能代码结束--------

		_self.cache.editor.addEventListener('input', function () {
		    AndroidInterface.setHtmlContent(_self.getHtml());
			AndroidInterface.staticWords(_self.staticWords());
		}, false);
	},
	initCache: function initCache() {
		var _self = this;
		_self.cache.editor = document.getElementById('editor');
//		_self.cache.title = document.getElementById('title');
//		_self.cache.line = document.getElementsByClassName('line')[0];
		_self.cache.editor.style.minHeight = window.innerHeight - 69 + 'px';
	},
	initSetting: function initSetting() {
		var _self = this;
		_self.setting.screenWidth = window.innerWidth - 20;
	},
	focus: function focus() {
		//聚焦
		var _self = this;
		var range = document.createRange();
		range.selectNodeContents(this.cache.editor);
		range.collapse(false);
		var select = window.getSelection();
		select.removeAllRanges();
		select.addRange(range);
		_self.cache.editor.focus();
	},
	setHtml:function setHtml(txt){
	var _self = this;
	_self.cache.editor.innerHTML  = "<div> "+txt+" </div>";
	},
	getHtml: function getHtml() {
		var _self = this;
		return _self.cache.editor.innerHTML.trim();
	},
//	getTitle : function getTitle(){
//	    var _self = this;
//	    return _self.cache.title.value.replace(/\s+/, '').trim();
//	},
	staticWords: function staticWords() {
		var _self = this;
		var content = _self.cache.editor.innerHTML.replace(/<div\sclass="tips">.*<\/div>|<\/?[^>]*>/g, '').replace(/\s+/, '').trim();
		return content.length;
	},
	saveRange: function saveRange() {
		//保存节点位置
		var _self = this;
		var selection = window.getSelection();
		if (selection.rangeCount > 0) {
			var range = selection.getRangeAt(0);
			var startContainer = range.startContainer,
			    startOffset = range.startOffset,
			    endContainer = range.endContainer,
			    endOffset = range.endOffset;
			_self.currentRange = {
				startContainer: startContainer,
				startOffset: startOffset,
				endContainer: endContainer,
				endOffset: endOffset
			};
		}
	},
	reduceRange: function reduceRange() {
		//还原节点位置
		var _self = this;
		var _self$currentRange = _self.currentRange,
		    startContainer = _self$currentRange.startContainer,
		    startOffset = _self$currentRange.startOffset,
		    endContainer = _self$currentRange.endContainer,
		    endOffset = _self$currentRange.endOffset;

		var range = document.createRange();
		var selection = window.getSelection();
		selection.removeAllRanges();
		range.setStart(startContainer, startOffset);
		range.setEnd(endContainer, endOffset);
		selection.addRange(range);
	},
	exec: function exec(command) {
		//执行指令
		var _self = this;
		if (_self.commandSet.indexOf(command) !== -1) {
			document.execCommand(command, false, null);
		} else {
			var value = '<' + command + '>';
			document.execCommand('formatBlock', false, value);
			_self.getEditItem({});
		}
	},
	getEditItem: function getEditItem(evt) {
		//通过点击时，去获得一个当前位置的所有状态
		var _self = this;
		var _self$schemeCache2 = _self.schemeCache,
		    STATE_SCHEME = _self$schemeCache2.STATE_SCHEME,
		    CHANGE_SCHEME = _self$schemeCache2.CHANGE_SCHEME,
		    IMAGE_SCHEME = _self$schemeCache2.IMAGE_SCHEME;

		if (evt.target && evt.target.tagName === 'A') {
			_self.cache.currentLink = evt.target;
			var name = evt.target.innerText;

			var id = evt.target.getAttribute('data-id');
			var href = evt.target.getAttribute('href');
			var url = evt.target.getAttribute('url');
			if (url==null) {
			window.location.href = CHANGE_SCHEME + encodeURI(id+'@_@' +name + '@_@' + href);
            }else{
            window.location.href = CHANGE_SCHEME + encodeURI(id+'@_@' +name + '@_@'+url);
            	}

		} else {
			if (evt.which == 8) {
				AndroidInterface.staticWords(_self.staticWords());
			}
			var items = [];
			_self.commandSet.forEach(function (item) {
				if (document.queryCommandState(item)) {
					items.push(item);
				}
			});
			if (document.queryCommandValue('formatBlock')) {
				items.push(document.queryCommandValue('formatBlock'));
			}
			window.location.href = STATE_SCHEME + encodeURI(items.join(','));
		}
	},
	setPlaceholder : function setPlaceholder(placeholder) {
	    var _self = this;
        _self.cache.editor.setAttribute("placeholder", "&nbsp;&nbsp;"+placeholder);
    },
	insertHtml: function insertHtml(html) {
		var _self = this;
		document.execCommand('insertHtml', false, html);
	},
	setBackgroundColor: function setBackgroundColor(color) {
	    var _self = this;
	    document.body.style.backgroundColor = color;
	    _self.cache.editor.backgroundColor = color;
	},
	setFontColor: function setFontColor(color) {
	       var _self = this;
        document.body.style.color = color;
         _self.cache.editor.color = color;
	},
	setLineColor: function setLineColor(color) {
	    var _self = this;
	    _self.cache.editor.style.borderColor = color;
	},
	insertLine: function insertLine() {
		var _self = this;
		var html = '<hr><div><br></div>';
		_self.insertHtml(html);
		_self.getEditItem({});
	},
//	insertLink: function insertLink(id,name, url,png) {
//		var _self = this;
//		var html = '<a data-id="' + id + '" href="' + url + '" class="editor-link">' + name + '</a>';
//		_self.insertHtml(html);
//	},


	insertLink: function insertLink(id,name, url) {
        var _self = this;
//        var files = 'file:///android_res/drawable/lianjietu.png'
        var html = '<a style="background:url(file:///android_res/drawable/lianjietu.png);background-size:20px 20px;background-repeat:no-repeat;padding-left:22px;box-sizing:border-box;background-position:0% 50%;" data-id="' + id + '" href="' + url + '" class="editor-link">' + name + '</a>';
//        var html = "<a style=\"background:url('file:///android_res/drawable/lianjietu.png');background-size:18px 16px;background-repeat:no-repeat;padding-left:22px;box-sizing:border-box;background-position:0% 50%;\" data-id="' + id + '" href="' + url + '" class="editor-link" >" + name + '</a >';
        _self.insertHtml(html);

		//------- 删除超链接功能代码开始--------
		_self.cache.links = document.querySelectorAll('a');
		//------- 删除超链接功能代码结束--------
    },
	changeLink: function changeLink(name, url) {
		var _self = this;
		var current = _self.cache.currentLink;
		var len = name.length;
		current.innerText = name;
		current.setAttribute('href', url);
		var selection = window.getSelection();
		var range = selection.getRangeAt(0).cloneRange();
		var _self$currentRange2 = _self.currentRange,
		    startContainer = _self$currentRange2.startContainer,
		    endContainer = _self$currentRange2.endContainer;

		selection.removeAllRanges();
		range.setStart(startContainer, len);
		range.setEnd(endContainer, len);
		selection.addRange(range);
	},
	insertImage: function insertImage(url, id, width, height) {
		var _self = this;
		var newWidth = 0,
		    newHeight = 0;
		var screenWidth = _self.setting.screenWidth;
			newWidth = screenWidth;
			newHeight = height * newWidth / width;
//		var image = '<br/>\n\t\t\t\t<img class="images" data-id="' + id + '" style="width: ' + newWidth + 'px; height: ' + newHeight + 'px;" src="' + url + '"/>\n';
		var image = '<br/><img class="images" data-id="' + id + '"  src="' + url + '"/>&nbsp;<br/>';
		_self.insertHtml(image);
//		var img = document.querySelector('img[data-id="' + id + '"]');
//		var imgBlock = img.parentNode;
//		imgBlock.parentNode.contentEditable = false;
//		imgBlock.addEventListener('click', function (e) {
//			e.stopPropagation();
//			var current = e.currentTarget;
//			var img = current.querySelector('.images');
//			var id = img.getAttribute('data-id');
//			window.location.href = _self.schemeCache.IMAGE_SCHEME + encodeURI(id);
//		}, false);
//		_self.imageCache.put(id, imgBlock.parentNode);
	},
	changeProcess: function changeProcess(id, process) {
		var _self = this;
		var block = _self.imageCache.get(id);
		var fill = block.querySelector('.fill');
		fill.style.width = process + '%';
		if (process == 100) {
			var cover = block.querySelector('.cover');
			var process = block.querySelector('.process');
			var imgBlock = block.querySelector('.img-block');
			imgBlock.removeChild(cover);
			imgBlock.removeChild(process);
		}
	},
	removeImage: function removeImage(id) {

		var _self = this;
		var block = _self.imageCache.get(id);
		block.parentNode.removeChild(block);
		_self.imageCache.remove(id);
	},
	uploadFailure: function uploadFailure(id) {
		var _self = this;
		var block = _self.imageCache.get(id);
		var del = block.querySelector('.delete');
		del.style.display = 'block';
	},
	uploadReload: function uploadReload(id) {
		var _self = this;
		var block = _self.imageCache.get(id);
		var del = block.querySelector('.delete');
		del.style.display = 'none';
	}
};

RE.init();
