package com.github.onecode369.wysiwyg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.ByteArrayOutputStream


class Utils private constructor() {
    companion object {
        fun toBase64(bitmap: Bitmap): String {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val bytes: ByteArray = baos.toByteArray()
            return Base64.encodeToString(bytes, Base64.NO_WRAP)
        }

        fun toBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            var width = drawable.intrinsicWidth
            width = if (width > 0) width else 1
            var height = drawable.intrinsicHeight
            height = if (height > 0) height else 1
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
            return bitmap
        }

        fun decodeResource(context: Context, resId: Int): Bitmap {
            return BitmapFactory.decodeResource(context.getResources(), resId)
        }

        val currentTime: Long
            get() = System.currentTimeMillis()
    }

    init {
        throw InstantiationException("This class is not for instantiation")
    }
}