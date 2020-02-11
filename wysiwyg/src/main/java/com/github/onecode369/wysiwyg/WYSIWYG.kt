package com.github.onecode369.wysiwyg

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList


class WYSIWYG @SuppressLint("SetJavaScriptEnabled") constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) :
    WebView(context, attrs, defStyleAttr) {
    enum class Type {
        BOLD,
        ITALIC,
        SUBSCRIPT,
        SUPERSCRIPT,
        STRIKETHROUGH,
        UNDERLINE,
        H1, H2, H3, H4, H5, H6,
        ORDEREDLIST,
        UNORDEREDLIST,
        JUSTIFYCENTER,
        JUSTIFYFULL,
        JUSTUFYLEFT,
        JUSTIFYRIGHT
    }



    interface OnTextChangeListener {
        fun onTextChange(text: String?)
    }

    interface OnDecorationStateListener {
        fun onStateChangeListener(
            text: String?,
            types: List<Type>?
        )
    }

    interface AfterInitialLoadListener {
        fun onAfterInitialLoad(isReady: Boolean)
    }

    private var isReady = false
    private var mContents: String? = null
    private var mTextChangeListener: OnTextChangeListener? = null
    private var mDecorationStateListener: OnDecorationStateListener? = null
    private var mLoadListener: AfterInitialLoadListener? = null

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.webViewStyle
    ) {
    }

    protected fun createWebviewClient(): EditorWebViewClient {
        return EditorWebViewClient()
    }

    fun setOnTextChangeListener(listener: OnTextChangeListener?) {
        mTextChangeListener = listener
    }

    fun setOnDecorationChangeListener(listener: OnDecorationStateListener?) {
        mDecorationStateListener = listener
    }

    fun setOnInitialLoadListener(listener: AfterInitialLoadListener?) {
        mLoadListener = listener
    }

    private fun callback(text: String) {
        mContents = text.replaceFirst(CALLBACK_SCHEME.toRegex(), "")
        if (mTextChangeListener != null) {
            mTextChangeListener!!.onTextChange(mContents)
        }
    }

    private fun stateCheck(text: String) {
        val state =
            text.replaceFirst(STATE_SCHEME.toRegex(), "")
                .toUpperCase(Locale.ENGLISH)
        val types: MutableList<Type> = ArrayList()
        for (type in Type.values()) {
            if (TextUtils.indexOf(state, type.name) != -1) {
                types.add(type)
            }
        }
        if (mDecorationStateListener != null) {
            mDecorationStateListener!!.onStateChangeListener(state, types)
        }
    }

    private fun applyAttributes(context: Context, attrs: AttributeSet?) {
        val attrsArray = intArrayOf(
            R.attr.gravity
        )
        val ta: TypedArray = context.obtainStyledAttributes(attrs, attrsArray)
        val gravity = ta.getInt(0, View.NO_ID)
        when (gravity) {
            Gravity.LEFT -> exec("javascript:editor.setTextAlign(\"left\")")
            Gravity.RIGHT -> exec("javascript:editor.setTextAlign(\"right\")")
            Gravity.TOP -> exec("javascript:editor.setVerticalAlign(\"top\")")
            Gravity.BOTTOM -> exec("javascript:editor.setVerticalAlign(\"bottom\")")
            Gravity.CENTER_VERTICAL -> exec("javascript:editor.setVerticalAlign(\"middle\")")
            Gravity.CENTER_HORIZONTAL -> exec("javascript:editor.setTextAlign(\"center\")")
            Gravity.CENTER -> {
                exec("javascript:editor.setVerticalAlign(\"middle\")")
                exec("javascript:editor.setTextAlign(\"center\")")
            }
        }
        ta.recycle()
    }

    // No handling
    var html: String?
        get() = mContents
        set(contents) {
            var contents = contents
            if (contents == null) {
                contents = ""
            }
            try {
                exec(
                    "javascript:editor.setHtml('" + URLEncoder.encode(
                        contents,
                        "UTF-8"
                    ).toString() + "');"
                )
            } catch (e: UnsupportedEncodingException) { // No handling
            }
            mContents = contents
        }

    fun setEditorFontColor(color: Int) {
        val hex = convertHexColorString(color)
        exec("javascript:editor.setBaseTextColor('$hex');")
    }

    fun setEditorFontSize(px: Int) {
        exec("javascript:editor.setBaseFontSize('" + px + "px');")
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        exec(
            "javascript:editor.setPadding('" + left + "px', '" + top + "px', '" + right + "px', '" + bottom
                    + "px');"
        )
    }

    override fun setPaddingRelative(
        start: Int,
        top: Int,
        end: Int,
        bottom: Int
    ) { // still not support RTL.
        setPadding(start, top, end, bottom)
    }

    fun setEditorBackgroundColor(color: Int) {
        setBackgroundColor(color)
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
    }

    override fun setBackgroundResource(resid: Int) {
        val bitmap: Bitmap = Utils.decodeResource(context, resid)
        val base64: String = Utils.toBase64(bitmap)
        bitmap.recycle()
        exec("javascript:editor.setBackgroundImage('url(data:image/png;base64,$base64)');")
    }

    override fun setBackground(background: Drawable) {
        val bitmap: Bitmap = Utils.toBitmap(background)
        val base64: String = Utils.toBase64(bitmap)
        bitmap.recycle()
        exec("javascript:editor.setBackgroundImage('url(data:image/png;base64,$base64)');")
    }

    fun setBackground(url: String) {
        exec("javascript:editor.setBackgroundImage('url($url)');")
    }

    fun setEditorWidth(px: Int) {
        exec("javascript:editor.setWidth('" + px + "px');")
    }

    fun setEditorHeight(px: Int) {
        exec("javascript:editor.setHeight('" + px + "px');")
    }

    fun setPlaceholder(placeholder: String) {
        exec("javascript:editor.setPlaceholder('$placeholder');")
    }

    fun setInputEnabled(inputEnabled: Boolean) {
        exec("javascript:editor.setInputEnabled($inputEnabled)")
    }

    fun loadCSS(cssFile: String) {
        val jsCSSImport = "(function() {" +
                "    var head  = document.getElementsByTagName(\"head\")[0];" +
                "    var link  = document.createElement(\"link\");" +
                "    link.rel  = \"stylesheet\";" +
                "    link.type = \"text/css\";" +
                "    link.href = \"" + cssFile + "\";" +
                "    link.media = \"all\";" +
                "    head.appendChild(link);" +
                "}) ();"
        exec("javascript:$jsCSSImport")
    }

    fun undo() {
        exec("javascript:editor.undo();")
    }

    fun redo() {
        exec("javascript:editor.redo();")
    }

    fun setBold() {
        exec("javascript:editor.setBold();")
    }

    fun setItalic() {
        exec("javascript:editor.setItalic();")
    }

    fun setSubscript() {
        exec("javascript:editor.setSubscript();")
    }

    fun setSuperscript() {
        exec("javascript:editor.setSuperscript();")
    }

    fun setStrikeThrough() {
        exec("javascript:editor.setStrikeThrough();")
    }

    fun setUnderline() {
        exec("javascript:editor.setUnderline();")
    }

    fun setTextColor(color: Int) {
        exec("javascript:editor.prepareInsert();")
        val hex = convertHexColorString(color)
        exec("javascript:editor.setTextColor('$hex');")
    }

    fun setTextBackgroundColor(color: Int) {
        exec("javascript:editor.prepareInsert();")
        val hex = convertHexColorString(color)
        exec("javascript:editor.setTextBackgroundColor('$hex');")
    }

    fun setFontSize(fontSize: Int) {
        if (fontSize > 7 || fontSize < 1) {
            Log.e("WYSIWYG", "Font size should have a value between 1-7")
        }
        exec("javascript:editor.setFontSize('$fontSize');")
    }

    fun removeFormat() {
        exec("javascript:editor.removeFormat();")
    }

    fun setHeading(heading: Int) {
        exec("javascript:editor.setHeading('$heading');")
    }

    fun setCode() {
        exec("javascript:editor.setCode();")
    }

    fun setIndent() {
        exec("javascript:editor.setIndent();")
    }

    fun setOutdent() {
        exec("javascript:editor.setOutdent();")
    }

    fun setAlignLeft() {
        exec("javascript:editor.setJustifyLeft();")
    }

    fun setAlignCenter() {
        exec("javascript:editor.setJustifyCenter();")
    }

    fun setAlignJustifyFull() {
        exec("javascript:editor.setJustifyFull();")
    }

    fun setAlignRight() {
        exec("javascript:editor.setJustifyRight();")
    }

    fun setBlockquote() {
        exec("javascript:editor.setBlockquote();")
    }

    fun setBullets() {
        exec("javascript:editor.setBullets();")
    }

    fun setNumbers() {
        exec("javascript:editor.setNumbers();")
    }

    fun insertImage(url: String, alt: String) {
        exec("javascript:editor.prepareInsert();")
        exec("javascript:editor.insertImage('$url', '$alt');")
    }

    fun insertLink(href: String, title: String) {
        exec("javascript:editor.prepareInsert();")
        exec("javascript:editor.insertLink('$href', '$title');")
    }

    fun insertTodo() {
        exec("javascript:editor.prepareInsert();")
        exec("javascript:editor.setTodo('" + Utils.currentTime.toString() + "');")
    }

    fun focusEditor() {
        requestFocus()
        exec("javascript:editor.focus();")
    }

    fun clearFocusEditor() {
        exec("javascript:editor.blurFocus();")
    }

    private fun convertHexColorString(color: Int): String {
        return String.format("#%06X", 0xFFFFFF and color)
    }

    protected fun exec(trigger: String) {
        if (isReady) {
            load(trigger)
        } else {
            postDelayed({ exec(trigger) }, 100)
        }
    }

    private fun load(trigger: String) {
        evaluateJavascript(trigger, null)
    }

    protected inner class EditorWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            isReady = url.equals(SETUP_HTML, ignoreCase = true)
            if (mLoadListener != null) {
                mLoadListener!!.onAfterInitialLoad(isReady)
            }
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            val decode: String
            decode = try {
                URLDecoder.decode(url, "UTF-8")
            } catch (e: UnsupportedEncodingException) { // No handling
                return false
            }
            if (TextUtils.indexOf(url, CALLBACK_SCHEME) == 0) {
                callback(decode)
                return true
            } else if (TextUtils.indexOf(url, STATE_SCHEME) == 0) {
                stateCheck(decode)
                return true
            }
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    companion object {
        private const val SETUP_HTML = "file:///android_asset/editor.html"
        private const val CALLBACK_SCHEME = "re-callback://"
        private const val STATE_SCHEME = "re-state://"
    }


    init {
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        settings.javaScriptEnabled = true
        webChromeClient = WebChromeClient()
        webViewClient = createWebviewClient()

        loadUrl(SETUP_HTML)
        applyAttributes(context, attrs)
    }
}
