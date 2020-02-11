package com.github.onecode369.wysiwyg_sample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.onecode369.wysiwyg.WYSIWYG
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "WYSIWYG Editor"

        val wysiwygEditor = editor as WYSIWYG
        wysiwygEditor.setEditorHeight(200)
        wysiwygEditor.setEditorFontSize(16)
        wysiwygEditor.setPadding(10, 10, 10, 10)
        wysiwygEditor.setPlaceholder("Insert your notes here...")

//        val mPreview = preview as TextView
//        wysiwygEditor.setOnTextChangeListener(OnTextChangeListener { text -> mPreview.setText(text) })

        action_undo.setOnClickListener{ wysiwygEditor.undo() }

        action_redo.setOnClickListener{ wysiwygEditor.redo() }

        action_bold.setOnClickListener{ wysiwygEditor.setBold() }

        action_italic.setOnClickListener{ wysiwygEditor.setItalic() }

        action_subscript.setOnClickListener{ wysiwygEditor.setSubscript() }

        action_superscript.setOnClickListener{ wysiwygEditor.setSuperscript() }

        action_strikethrough.setOnClickListener{ wysiwygEditor.setStrikeThrough() }

        action_underline.setOnClickListener { wysiwygEditor.setUnderline() }

        action_heading1.setOnClickListener{
            wysiwygEditor.setHeading(
                1
            )
        }

        action_heading2.setOnClickListener{
            wysiwygEditor.setHeading(
                2
            )
        }

        action_heading3.setOnClickListener{
            wysiwygEditor.setHeading(
                3
            )
        }

        action_heading4.setOnClickListener{
            wysiwygEditor.setHeading(
                4
            )
        }

        action_heading5.setOnClickListener{
            wysiwygEditor.setHeading(
                5
            )
        }

        action_heading6.setOnClickListener{
            wysiwygEditor.setHeading(
                6
            )
        }

        action_txt_color.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                wysiwygEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        action_bg_color.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(v: View) {
                wysiwygEditor.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        action_indent.setOnClickListener{wysiwygEditor.setIndent() }

        action_outdent.setOnClickListener { wysiwygEditor.setOutdent() }

        action_align_left.setOnClickListener{ wysiwygEditor.setAlignLeft() }

        action_align_center.setOnClickListener{ wysiwygEditor.setAlignCenter() }

        action_align_right.setOnClickListener { wysiwygEditor.setAlignRight() }

        action_align_justify.setOnClickListener { wysiwygEditor.setAlignJustifyFull() }

        action_blockquote.setOnClickListener { wysiwygEditor.setBlockquote() }

        action_insert_bullets.setOnClickListener { wysiwygEditor.setBullets() }

        action_insert_numbers.setOnClickListener { wysiwygEditor.setNumbers() }

        action_insert_image.setOnClickListener{
            wysiwygEditor.insertImage(
                "https://i.postimg.cc/JzL891Fm/maxresdefault.jpg",
                "Night Sky"
            )
        }

        action_insert_link.setOnClickListener{
            wysiwygEditor.insertLink(
                "https://github.com/onecode369",
                "One Code"
            )
        }

        action_insert_checkbox.setOnClickListener{ wysiwygEditor.insertTodo() }

        var visible = false

        preview.setOnClickListener {
            if(!visible){
                wysiwygEditor.setInputEnabled(false)
                preview.setImageResource(R.drawable.visibility_off)
            }else{
                wysiwygEditor.setInputEnabled(true)
                preview.setImageResource(R.drawable.visibility)
            }
            visible = !visible
        }

        insert_latex.setOnClickListener {
            if(latext_editor.visibility == View.GONE) {
                latext_editor.visibility = View.VISIBLE
                submit_latex.setOnClickListener {
                    wysiwygEditor.insertLatex(latex_equation.text.toString())
                }
            }else{
                latext_editor.visibility = View.GONE
            }
        }

        insert_code.setOnClickListener{ wysiwygEditor.setCode() }
    }
}
