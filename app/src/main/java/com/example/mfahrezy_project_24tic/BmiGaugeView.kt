package com.example.mfahrezy_project_24tic

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import java.util.Locale

class BmiGaugeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val arcBounds = RectF()

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.BUTT
    }

    private val needlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 6f
        color = ContextCompat.getColor(context, R.color.text_ink_dark)
    }

    private val pivotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.primary_teal)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        color = ContextCompat.getColor(context, R.color.text_ink_dark)
        isFakeBoldText = true
    }

    private val statusPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        color = ContextCompat.getColor(context, R.color.primary_teal)
        isFakeBoldText = true
    }

    private var currentBmi = 22.8f
    private var statusText = "Normal"

    private val colorUnderweight = ContextCompat.getColor(context, R.color.bmi_underweight)
    private val colorNormal = ContextCompat.getColor(context, R.color.bmi_normal)
    private val colorOverweight = ContextCompat.getColor(context, R.color.bmi_overweight)
    private val colorObese = ContextCompat.getColor(context, R.color.bmi_obese)

    fun setBmiScore(score: Float, status: String = "Normal", animate: Boolean = true) {
        statusText = status
        if (animate) {
            val animator = ValueAnimator.ofFloat(currentBmi, score).apply {
                duration = 1000
                interpolator = DecelerateInterpolator()
                addUpdateListener { animation ->
                    currentBmi = animation.animatedValue as Float
                    invalidate()
                }
            }
            animator.start()
        } else {
            currentBmi = score
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        if (width <= 0 || height <= 0) return

        val padding = 40f
        val strokeWidth = 36f
        val radius = (width - padding * 2) / 2f
        val centerX = width / 2f
        val centerY = height * 0.7f

        arcBounds.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        arcPaint.strokeWidth = strokeWidth

        // Draw 4 segments of semi-circle (180 degrees total from 180° to 360°)
        // 1. Underweight (180° to 225°, sweep 45°)
        arcPaint.color = colorUnderweight
        canvas.drawArc(arcBounds, 180f, 45f, false, arcPaint)

        // 2. Normal (225° to 270°, sweep 45°)
        arcPaint.color = colorNormal
        canvas.drawArc(arcBounds, 225f, 45f, false, arcPaint)

        // 3. Overweight (270° to 315°, sweep 45°)
        arcPaint.color = colorOverweight
        canvas.drawArc(arcBounds, 270f, 45f, false, arcPaint)

        // 4. Obese (315° to 360°, sweep 45°)
        arcPaint.color = colorObese
        canvas.drawArc(arcBounds, 315f, 45f, false, arcPaint)

        // Calculate Needle Angle
        // Map BMI score (min 12 to max 38) to angle (180° to 360°)
        val minBmi = 12f
        val maxBmi = 38f
        val clampedBmi = currentBmi.coerceIn(minBmi, maxBmi)
        val angleProgress = (clampedBmi - minBmi) / (maxBmi - minBmi)
        val needleAngleRad = Math.toRadians((180 + angleProgress * 180).toDouble())

        // Needle length
        val needleLength = radius - strokeWidth - 20f
        val needleX = centerX + (needleLength * Math.cos(needleAngleRad)).toFloat()
        val needleY = centerY + (needleLength * Math.sin(needleAngleRad)).toFloat()

        // Draw Needle Line
        canvas.drawLine(centerX, centerY, needleX, needleY, needlePaint)

        // Draw Pivot Circle
        canvas.drawCircle(centerX, centerY, 16f, pivotPaint)

        // Draw BMI Value Text
        textPaint.textSize = height * 0.15f
        canvas.drawText(String.format(Locale.US, "%.1f", currentBmi), centerX, centerY + height * 0.18f, textPaint)

        // Draw Status Text
        statusPaint.textSize = height * 0.08f
        canvas.drawText(statusText.uppercase(Locale.US), centerX, centerY + height * 0.28f, statusPaint)
    }
}
