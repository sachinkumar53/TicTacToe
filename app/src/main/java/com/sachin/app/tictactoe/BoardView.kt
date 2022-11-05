package com.sachin.app.tictactoe

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.sachin.app.tictactoe.model.Fillable
import com.sachin.app.tictactoe.util.GraphicUtil
import com.sachin.app.tictactoe.util.getAtPoint
import kotlin.math.min

class BoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr), View.OnTouchListener {

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        setOnTouchListener(this)
    }

    private var size = 800
    private var cellSize = 0
    private var onPointClickListener: OnPointClickListener? = null

    private val paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.parseColor("#f6e58d")
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                STROKE_WIDTH,
                resources.displayMetrics
            )
    }

    private val glowPaint = Paint().apply {
        set(paint)
        color = Color.parseColor("#ffd32a")
        strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            (STROKE_WIDTH + 3F),
            resources.displayMetrics
        )

        maskFilter = BlurMaskFilter(9F, BlurMaskFilter.Blur.NORMAL)
    }

    private val grid = Path()

    private val o = context.getDrawable(R.drawable.o)!!
    private val x = context.getDrawable(R.drawable.x)!!
    private var showWinPonts = arrayListOf<Fillable>()

    private val bitmapO by lazy {
        GraphicUtil.getGlowBitmapFromDrawable(
            o,
            Color.parseColor("#90dcf5"),
            Color.parseColor("#4bcffa")
        )
    }

    private val bitmapX by lazy {
        GraphicUtil.getGlowBitmapFromDrawable(
            x,
            Color.parseColor("#fc665d"),
            Color.parseColor("#ff3f34")
        )
    }

    private val bitmapOWin by lazy {
        GraphicUtil.getGlowBitmapFromDrawable(
            context.getDrawable(R.drawable.o)!!,
            Color.WHITE,
            Color.WHITE
        )
    }

    private val bitmapXWin by lazy {
        GraphicUtil.getGlowBitmapFromDrawable(
            context.getDrawable(R.drawable.x)!!,
            Color.WHITE,
            Color.WHITE
        )
    }

    private var drawPoint = false
    private val filledPositions = arrayListOf<Fillable>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        size = min(w, h)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        cellSize = (size - paddingTop - paddingBottom) / 3

        grid.moveTo(paddingLeft.toFloat(), paddingTop + cellSize.toFloat())
        grid.lineTo((w - paddingRight).toFloat(), paddingTop + cellSize.toFloat())

        grid.moveTo(paddingLeft.toFloat(), paddingTop + 2 * cellSize.toFloat())
        grid.lineTo((w - paddingRight).toFloat(), paddingTop + 2 * cellSize.toFloat())

        grid.moveTo(paddingLeft + cellSize.toFloat(), paddingTop.toFloat())
        grid.lineTo(paddingLeft + cellSize.toFloat(), (h - paddingBottom).toFloat())

        grid.moveTo(paddingLeft + 2 * cellSize.toFloat(), paddingTop.toFloat())
        grid.lineTo(paddingLeft + 2 * cellSize.toFloat(), (h - paddingBottom).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(grid, glowPaint)
        canvas.drawPath(grid, paint)

        if (!drawPoint) return

        filledPositions.forEach {
            if (it.fill.equals("x", true)) {
                if (showWinPonts.contains(it)) {
                    canvas.drawBitmap(
                        bitmapXWin,
                        (paddingLeft + (cellSize * it.x) + (cellSize - bitmapX.width) / 2).toFloat(),
                        (paddingTop + (cellSize * it.y) + (cellSize - bitmapX.height) / 2).toFloat(),
                        paint
                    )
                } else {
                    canvas.drawBitmap(
                        bitmapX,
                        (paddingLeft + (cellSize * it.x) + (cellSize - bitmapX.width) / 2).toFloat(),
                        (paddingTop + (cellSize * it.y) + (cellSize - bitmapX.height) / 2).toFloat(),
                        paint
                    )
                }
            } else {
                if (showWinPonts.contains(it)) {
                    canvas.drawBitmap(
                        bitmapOWin,
                        (paddingLeft + (cellSize * it.x) + (cellSize - bitmapX.width) / 2).toFloat(),
                        (paddingTop + (cellSize * it.y) + (cellSize - bitmapX.height) / 2).toFloat(),
                        paint
                    )
                } else {
                    canvas.drawBitmap(
                        bitmapO,
                        (paddingLeft + (cellSize * it.x) + (cellSize - bitmapX.width) / 2).toFloat(),
                        (paddingTop + (cellSize * it.y) + (cellSize - bitmapX.height) / 2).toFloat(),
                        paint
                    )
                }
            }
        }


        drawPoint = false
    }

    fun showWinAndReset(fillables: ArrayList<Fillable>, delay: Long) {
        showWinPonts = fillables
        postDelayed({
            showWinPonts.clear()
            filledPositions.clear()
            invalidate()
        }, delay)
    }

    fun getAllFilledPoints(): ArrayList<Fillable> = filledPositions

    fun drawFillable(fillable: Fillable){
        if (fillable.fill == "x"){
            drawX(fillable.x,fillable.y)
        }else{
            drawO(fillable.x,fillable.y)
        }
    }
    fun drawX(point: Point) = drawX(point.x, point.y)

    fun drawO(point: Point) = drawO(point.x, point.y)

    fun drawX(positionX: Int, positionY: Int) {
        drawPoint = true
        filledPositions.add(
            Fillable(
                "x",
                Point(positionX, positionY)
            )
        )
        invalidate()
    }

    fun drawO(positionX: Int, positionY: Int) {
        drawPoint = true
        filledPositions.add(
            Fillable(
                "o",
                Point(positionX, positionY)
            )
        )
        invalidate()
    }

    fun detectCell(pointIn: Point): Point {
        val pointOut = Point()

        if (pointIn.x < (cellSize + paddingLeft))
            pointOut.x = 0
        else if (pointIn.x > (cellSize + paddingLeft) && pointIn.x < (cellSize * 2) + paddingLeft)
            pointOut.x = 1
        else pointOut.x = 2

        if (pointIn.y < (cellSize + paddingTop))
            pointOut.y = 0
        else if (pointIn.y > (cellSize + paddingTop) && pointIn.y < (cellSize * 2) + paddingTop)
            pointOut.y = 1
        else pointOut.y = 2

        return pointOut
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val point = detectCell(Point(event.x.toInt(), event.y.toInt()))

            onPointClickListener?.onPointClick(
                point,
                filledPositions.getAtPoint(point.x, point.y) != null
            )
            return true
        }

        return false
    }

    fun setOnPointClickListener(onPointClickListener: OnPointClickListener) {
        this.onPointClickListener = onPointClickListener
    }

    companion object {
        private const val STROKE_WIDTH = 3F
    }

    interface OnPointClickListener {
        fun onPointClick(point: Point, filled: Boolean)
    }
}