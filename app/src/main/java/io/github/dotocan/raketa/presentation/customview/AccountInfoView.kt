package io.github.dotocan.raketa.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import io.github.dotocan.raketa.R

/**
 * Created by dotocan on 3.3.2018..
 */
class AccountInfoView : View {

    private lateinit var namePaint: TextPaint
    private lateinit var onlineStatusTextPaint: TextPaint
    private lateinit var onlineStatusPaint: Paint

    private var colorOnline: Int = 0
    private var colorAway: Int = 0
    private var colorBusy: Int = 0
    private var colorInvisible: Int = 0

    private var online = ""
    private var away = ""
    private var busy = ""
    private var invisible = ""
    private var status = ""


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        online = context.getString(R.string.online).toLowerCase()
        away = context.getString(R.string.away).toLowerCase()
        busy = context.getString(R.string.busy).toLowerCase()
        invisible = context.getString(R.string.invisible).toLowerCase()
        colorOnline = ContextCompat.getColor(context, R.color.colorStatusOnline)
        colorAway = ContextCompat.getColor(context, R.color.colorStatusBusy)
        colorBusy = ContextCompat.getColor(context, R.color.colorStatusAway)
        colorInvisible = ContextCompat.getColor(context, R.color.colorStatusInvisible)

        namePaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        namePaint.color = ContextCompat.getColor(context, android.R.color.white)
        namePaint.textSize = dpOrSpToPx(24f)

        onlineStatusTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        onlineStatusTextPaint.color = ContextCompat.getColor(context, R.color.colorStatusText)
        onlineStatusTextPaint.textSize = dpOrSpToPx(18f)

        onlineStatusPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        onlineStatusPaint.color = colorOnline
    }

    private fun dpOrSpToPx(dpOrSp: Float) = dpOrSp * resources.displayMetrics.density

    override fun onDraw(canvas: Canvas?) {
        val canvasWidth = canvas?.width ?: 0
        val canvasHeight = canvas?.height ?: 0

        canvas?.drawText(context.getString(R.string.john_doe), 0f, 50f, namePaint)
        canvas?.drawText(status, 50f, 100f, onlineStatusTextPaint)
        canvas?.drawCircle(25f, 85f, dpOrSpToPx(6f), onlineStatusPaint)
    }

    public fun setOnlineStatus(onlineStatus: String) {
        status = onlineStatus.capitalize()
        when (onlineStatus) {
            online -> {
                onlineStatusPaint.color = colorOnline
            }
            away -> {
                onlineStatusPaint.color = colorAway
            }
            busy -> {
                onlineStatusPaint.color = colorBusy
            }
            invisible -> {
                onlineStatusPaint.color = colorInvisible
            }
        }

        invalidate()
    }

}