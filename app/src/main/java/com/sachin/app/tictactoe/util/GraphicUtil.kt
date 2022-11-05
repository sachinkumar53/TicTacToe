package com.sachin.app.tictactoe.util

import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import org.jetbrains.annotations.NotNull

object GraphicUtil {

    fun getGlowBitmapFromDrawable(
        @NotNull drawable: Drawable,
        @ColorInt tintColor: Int,
        @ColorInt glowColor: Int
    ): Bitmap {

        val margin = 24
        val halfMargin = margin / 2F

        val src = drawableToBitmap(
            drawable,
            tintColor
        )
        val alpha = src.extractAlpha()

        val w = src.width
        val h = src.height

        val bitmap = Bitmap.createBitmap(w + margin, h + margin, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.run {
            isDither = false
            isAntiAlias = false
            color = glowColor
            maskFilter = BlurMaskFilter(15F, BlurMaskFilter.Blur.OUTER)
        }

        canvas.drawBitmap(alpha, halfMargin, halfMargin, paint)

        canvas.drawBitmap(src, halfMargin, halfMargin, null)

        return bitmap
    }

    fun drawableToBitmap(@NotNull drawable: Drawable, @ColorInt tint: Int? = null): Bitmap {
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        tint?.let { drawable.mutate().setTint(it) }
        drawable.draw(canvas)
        return bitmap
    }
}