var editor = {};

editor.currentSelection = {
    "startContainer": 0,
    "startOffset": 0,
    "endContainer": 0,
    "endOffset": 0};

editor.editor = document.getElementById('editor');



document.addEventListener("selectionchange", function() { editor.backuprange(); });

// Initializations
editor.callback = function() {
    window.location.href = "re-callback://" + encodeURI(editor.getHtml());
}

editor.setHtml = function(contents) {
    editor.editor.innerHTML = decodeURIComponent(contents.replace(/\+/g, '%20'));
}

editor.getHtml = function() {
    return editor.editor.innerHTML;
}

editor.getText = function() {
    return editor.editor.innerText;
}

editor.setBaseTextColor = function(color) {
    editor.editor.style.color  = color;
}

editor.setBaseFontSize = function(size) {
    editor.editor.style.fontSize = size;
}

editor.setPadding = function(left, top, right, bottom) {
  editor.editor.style.paddingLeft = left;
  editor.editor.style.paddingTop = top;
  editor.editor.style.paddingRight = right;
  editor.editor.style.paddingBottom = bottom;
}

editor.setBackgroundColor = function(color) {
    document.body.style.backgroundColor = color;
}

editor.setBackgroundImage = function(image) {
    editor.editor.style.backgroundImage = image;
}

editor.setWidth = function(size) {
    editor.editor.style.minWidth = size;
}

editor.setHeight = function(size) {
    editor.editor.style.height = size;
}

editor.setTextAlign = function(align) {
    editor.editor.style.textAlign = align;
}

editor.setVerticalAlign = function(align) {
    editor.editor.style.verticalAlign = align;
}

editor.setPlaceholder = function(placeholder) {
    editor.editor.setAttribute("placeholder", placeholder);
}

editor.setInputEnabled = function(inputEnabled) {
    editor.editor.contentEditable = String(inputEnabled);
}

editor.undo = function() {
    document.execCommand('undo', false, null);
}

editor.redo = function() {
    document.execCommand('redo', false, null);
}

editor.setBold = function() {
    document.execCommand('bold', false, null);
}

editor.setItalic = function() {
    document.execCommand('italic', false, null);
}

editor.setSubscript = function() {
    if(window.getSelection().toString() != "")
    var html = '<sub>' + window.getSelection().toString() + '</sub> &nbsp;';
    editor.insertHTML(html);
}

editor.setSuperscript = function() {
    if(window.getSelection().toString() != "")
    var html = '<sup>' + window.getSelection().toString() + '</sup> &nbsp;';
    editor.insertHTML(html);
}

editor.setStrikeThrough = function() {
    document.execCommand('strikeThrough', false, null);
}

editor.setUnderline = function() {
    document.execCommand('underline', false, null);
}

editor.setBullets = function() {
    document.execCommand('insertUnorderedList', false, null);
}

editor.setNumbers = function() {
    document.execCommand('insertOrderedList', false, null);
}

editor.setTextColor = function(color) {
    editor.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('foreColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

editor.setTextBackgroundColor = function(color) {
    editor.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('hiliteColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

editor.setFontSize = function(fontSize){
    document.execCommand("fontSize", false, fontSize);
}

editor.setHeading = function(heading) {
    document.execCommand('formatBlock', false, '<h'+heading+'>');
}

editor.setIndent = function() {
    document.execCommand('indent', false, null);
}

editor.setOutdent = function() {
    document.execCommand('outdent', false, null);
}

editor.setJustifyLeft = function() {
    document.execCommand('justifyLeft', false, null);
}

editor.setJustifyCenter = function() {
    document.execCommand('justifyCenter', false, null);
}

editor.setJustifyRight = function() {
    document.execCommand('justifyRight', false, null);
}

editor.setJustifyFull = function() {
    document.execCommand('justifyFull', false, null);
}

editor.setBlockquote = function() {
    if(window.getSelection().toString() != "")
    var html = '<p style="color: #000 !important;background-color: #f1f1f1 !important;border-left: 6px solid #ccc !important;padding:10px">' + window.getSelection().toString() + '</p></br>';
    editor.insertHTML(html);
}

editor.setCode = function() {
    if(window.getSelection().toString() != ""){
        var code = window.getSelection().toString().replace(/</ig, '&lt;').replace(/>/ig, '&gt;');
        var html ='<div class="w3-panel w3-card w3-light-grey"><div class="w3-code htmlHigh notranslate">'+code+'</div></div></br>'
    }
    editor.insertHTML(html);
}

editor.insertImage = function(url, alt) {
    var html = '<img style="width:100%;max-width:400px" src="' + url + '" alt="' + alt + '" />';
    editor.insertHTML(html);
}

editor.insertLatex = function(latex) {
    var html = '<img src="https://private.codecogs.com/png.download?' + latex + '" alt="' + latex + '" />';
    editor.insertHTML(html);
}

editor.insertHTML = function(html) {
    editor.restorerange();
    document.execCommand('insertHTML', false, html);
}

editor.insertLink = function(url, title) {
    editor.restorerange();
    var sel = document.getSelection();
    if (sel.toString().length == 0) {
        document.execCommand("insertHTML",false,"<a href='"+url+"'>"+title+"</a>");
    } else if (sel.rangeCount) {
       var el = document.createElement("a");
       el.setAttribute("href", url);
       el.setAttribute("title", title);

       var range = sel.getRangeAt(0).cloneRange();
       range.surroundContents(el);
       sel.removeAllRanges();
       sel.addRange(range);
   }
    editor.callback();
}

editor.setTodo = function(text) {
    var html = '<input class="w3-checkbox" type="checkbox" name="'+ text +'" value="'+ text +'"/> &nbsp;';
    document.execCommand('insertHTML', false, html);
}

editor.setTodo = function(text) {
    var html = '<input class="w3-checkbox" type="checkbox" name="'+ text +'" value="'+ text +'"/> &nbsp;';
    document.execCommand('insertHTML', false, html);
}

editor.prepareInsert = function() {
    RE.backuprange();
}

editor.backuprange = function(){
    var selection = window.getSelection();
    if (selection.rangeCount > 0) {
      var range = selection.getRangeAt(0);
      editor.currentSelection = {
          "startContainer": range.startContainer,
          "startOffset": range.startOffset,
          "endContainer": range.endContainer,
          "endOffset": range.endOffset};
    }
}

editor.restorerange = function(){
    var selection = window.getSelection();
    selection.removeAllRanges();
    var range = document.createRange();
    range.setStart(editor.currentSelection.startContainer, editor.currentSelection.startOffset);
    range.setEnd(editor.currentSelection.endContainer, editor.currentSelection.endOffset);
    selection.addRange(range);
}

editor.enabledEditingItems = function(e) {
    var items = [];
    if (document.queryCommandState('bold')) {
        items.push('bold');
    }
    if (document.queryCommandState('italic')) {
        items.push('italic');
    }
    if (document.queryCommandState('subscript')) {
        items.push('subscript');
    }
    if (document.queryCommandState('superscript')) {
        items.push('superscript');
    }
    if (document.queryCommandState('strikeThrough')) {
        items.push('strikeThrough');
    }
    if (document.queryCommandState('underline')) {
        items.push('underline');
    }
    if (document.queryCommandState('insertOrderedList')) {
        items.push('orderedList');
    }
    if (document.queryCommandState('insertUnorderedList')) {
        items.push('unorderedList');
    }
    if (document.queryCommandState('justifyCenter')) {
        items.push('justifyCenter');
    }
    if (document.queryCommandState('justifyFull')) {
        items.push('justifyFull');
    }
    if (document.queryCommandState('justifyLeft')) {
        items.push('justifyLeft');
    }
    if (document.queryCommandState('justifyRight')) {
        items.push('justifyRight');
    }
    if (document.queryCommandState('insertHorizontalRule')) {
        items.push('horizontalRule');
    }
    var formatBlock = document.queryCommandValue('formatBlock');
    if (formatBlock.length > 0) {
        items.push(formatBlock);
    }

    window.location.href = "re-state://" + encodeURI(items.join(','));
}

editor.focus = function() {
    var range = document.createRange();
    range.selectNodeContents(editor.editor);
    range.collapse(false);
    var selection = window.getSelection();
    selection.removeAllRanges();
    selection.addRange(range);
    editor.editor.focus();
}

editor.blurFocus = function() {
    editor.editor.blur();
}

editor.removeFormat = function() {
    document.execCommand('removeFormat', false, null);
}

// Event Listeners
editor.editor.addEventListener("input", editor.callback);
editor.editor.addEventListener("keyup", function(e) {
    var KEY_LEFT = 37, KEY_RIGHT = 39;
    if (e.which == KEY_LEFT || e.which == KEY_RIGHT) {
        editor.enabledEditingItems(e);
    }
});
editor.editor.addEventListener("click", editor.enabledEditingItems);
