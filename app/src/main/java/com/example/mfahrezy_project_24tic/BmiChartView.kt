package com.example.mfahrezy_project_24tic

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import java.util.Locale

class BmiChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bmiData: List<Float> = listOf(24.5f, 23.8f, 23.2f, 22.8f)

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        color = ContextCompat.getColor(context, R.color.primary_teal)
        strokeCap = Paint.Cap.ROUND
    }

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.primary_teal)
    }

    private val dotInnerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.white)
    }

    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        color = ContextCompat.getColor(context, R.color.gray_border)
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 32f
        color = ContextCompat.getColor(context, R.color.text_ink_dark)
        isFakeBoldText = true
    }

    private val path = Path()

    fun setBmiData(records: List<BmiRecord>) {
        if (records.isNotEmpty()) {
            bmiData = records.take(6).map { it.bmiScore }.reversed()
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        if (width <= 0 || height <= 0 || bmiData.isEmpty()) return

        val paddingX = 60f
        val paddingY = 60f
        val chartWidth = width - paddingX * 2
        val chartHeight = height - paddingY * 2

        val minBmi = (bmiData.minOrNull() ?: 18f) - 2f
        val maxBmi = (bmiData.maxOrNull() ?: 28f) + 2f
        val bmiRange = if (maxBmi - minBmi <= 0) 10f else maxBmi - minBmi

        // Draw horizontal grid lines
        val gridCount = 3
        for (i in 0..gridCount) {
            val y = paddingY + chartHeight - (i.toFloat() / gridCount) * chartHeight
            canvas.drawLine(paddingX, y, width - paddingX, y, gridPaint)
        }

        path.reset()
        val points = mutableListOf<Pair<Float, Float>>()

        val stepX = if (bmiData.size > 1) chartWidth / (bmiData.size - 1) else 0f

        for (i in bmiData.indices) {
            val x = paddingX + i * stepX
            val bmi = bmiData[i]
            val y = paddingY + chartHeight - ((bmi - minBmi) / bmiRange) * chartHeight
            points.add(Pair(x, y))

            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        // Draw Line
        canvas.drawPath(path, linePaint)

        // Draw Dots and Values
        for (i in points.indices) {
            val (x, y) = points[i]
            val bmi = bmiData[i]

            // Outer dot
            canvas.drawCircle(x, y, 14f, dotPaint)
            // Inner white dot
            canvas.drawCircle(x, y, 7f, dotInnerPaint)

            // Value text
            canvas.drawText(String.format(Locale.US, "%.1f", bmi), x, y - 22f, textPaint)
        }
    }
}
