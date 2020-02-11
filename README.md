# WYSIWYG
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![](https://jitpack.io/v/onecode369/WYSIWYG.svg)](https://jitpack.io/#onecode369/WYSIWYG)

``WYSIWYG`` is a ``Rich Text Editor`` for ``Android`` made in ``Kotlin``

## Demo

<img src="https://github.com/onecode369/WYSIWYG/blob/master/docs/GIF-200210_225312.gif" width="25%"/>

Toolbar Preview


<img src="https://github.com/onecode369/WYSIWYG/blob/master/docs/20200210_200910.gif" width="25%"/>

## Supported Functions

<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Undo <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Redo <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Bold <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Italic <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Subscript <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Superscript <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Strikethrough <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Underlined <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Header 1 <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Header 2 <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Header 3 <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Header 4 <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Header 5 <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Header 6 <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> TextColor <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> BackgroundColor <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Intdent <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Outdent <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Text Align Left <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Text Align Center <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Text Align Right <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Text Align Justify All <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Ordered List <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Unordered List <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Quote <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Insert Image <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Insert Link <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Insert Code <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Todolist List <br/>

## Changeable Atributes of Editor

<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Font Size <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Background Color <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Width <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Height <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Placeholder <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> Load CSS <br/>
<img src="https://i.postimg.cc/yNY3G3xM/correct.png" width="2%"/> State Callback <br/>

## How you can add it to your app

Add this in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

```
dependencies {
  implementation 'com.github.onecode369:WYSIWYG:$WYSIWYG_version'
}
```

## How to implement WYSIWYG Editor to your app

### Adding editor to your layout
 You can see the [XML file](https://github.com/onecode369/WYSIWYG/blob/master/app/src/main/res/layout/activity_main.xml) to understand how to implement it to your app

### Using it with your application
You can see the [Activity File](https://github.com/onecode369/WYSIWYG/blob/master/app/src/main/java/com/github/onecode369/wysiwyg_sample/MainActivity.kt) to understand how to use it
<br/>

----
### Works on Android SDK 21 and above
---

>Devloped by "One Code" - @onecode369
