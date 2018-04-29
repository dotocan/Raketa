package io.github.dotocan.raketa.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import io.github.dotocan.raketa.R

/**
 * Created by dotocan on 14.3.2018..
 */

class OnlineStatusView : View {

    constructor(context: Context?) : super(context) { init() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }

    private lateinit var circlePaint: Paint
    private var circleX: Float = 0.0f
    private var circleY: Float = 0.0f
    private var circleRadius: Float = 0.0f

    private var colorOnline: Int = 0
    private var colorAway: Int = 0
    private var colorBusy: Int = 0
    private var colorInvisible: Int = 0

    private var online = ""
    private var away = ""
    private var busy = ""
    private var invisible = ""
    private var status = ""

    private fun init() {
        online = context.getString(R.string.online).toLowerCase()
        away = context.getString(R.string.away).toLowerCase()
        busy = context.getString(R.string.busy).toLowerCase()
        invisible = context.getString(R.string.invisible).toLowerCase()
        colorOnline = ContextCompat.getColor(context, R.color.colorStatusOnline)
        colorAway = ContextCompat.getColor(context, R.color.colorStatusBusy)
        colorBusy = ContextCompat.getColor(context, R.color.colorStatusAway)
        colorInvisible = ContextCompat.getColor(context, R.color.colorStatusInvisible)

        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleX = (w / 2).toFloat()
        circleY = (h / 2).toFloat()
        circleRadius = (Math.min(w, h) / 2 * 0.8).toFloat()
    }

    private fun dpOrSpToPx(dpOrSp: Float) = dpOrSp * resources.displayMetrics.density

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(circleX, circleY, circleRadius, circlePaint)
    }

    public fun setOnlineStatus(onlineStatus: String) {
        status = onlineStatus.capitalize()
        when (onlineStatus) {
            online -> { circlePaint.color = colorOnline }
            away -> { circlePaint.color = colorAway }
            busy -> { circlePaint.color = colorBusy }
            invisible -> { circlePaint.color = colorInvisible }
        }

        invalidate()
    }
}