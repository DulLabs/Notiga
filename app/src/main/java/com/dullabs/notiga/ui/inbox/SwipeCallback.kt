package com.dullabs.notiga.ui.inbox

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dullabs.notiga.R
import kotlin.properties.Delegates
import kotlin.math.max
import kotlin.math.min

abstract class SwipeCallback(mContext: Context) : ItemTouchHelper.Callback() {

    private var mClearPaint: Paint = Paint()
    private var mDeleteBackground: ColorDrawable = ColorDrawable()
    private var mDeleteBackgroundColor by Delegates.notNull<Int>()
    private var mDeleteDrawable: Drawable
    private var mPauseBackground: ColorDrawable = ColorDrawable()
    private var mPauseBackgroundColor by Delegates.notNull<Int>()
    private var mPauseDrawable: Drawable
    private var mIntrinsicWidthDelete by Delegates.notNull<Int>()
    private var mIntrinsicHeightDelete by Delegates.notNull<Int>()
    private var mIntrinsicWidthPause by Delegates.notNull<Int>()
    private var mIntrinsicHeightPause by Delegates.notNull<Int>()

    init {
        mDeleteBackgroundColor = Color.parseColor("#F68167")
        mClearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        mDeleteDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_delete)!!
        mPauseBackgroundColor = Color.parseColor("#8BD343")
        mPauseDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_pause)!!
        mIntrinsicWidthDelete = mDeleteDrawable.intrinsicWidth
        mIntrinsicHeightDelete = mDeleteDrawable.intrinsicHeight
        mIntrinsicWidthPause = mPauseDrawable.intrinsicWidth
        mIntrinsicHeightPause = mPauseDrawable.intrinsicHeight
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView: View = viewHolder.itemView

        revealSwipeOptions(c, itemView, dX)
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            getTranslateX(itemView, dX),
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.5f
    }

    private fun revealSwipeOptions(c: Canvas, itemView: View, dX: Float) {
        val itemHeight: Int = itemView.height
        if (dX > 0) {
            mDeleteBackground.color = mDeleteBackgroundColor
            mDeleteBackground.setBounds(
                itemView.left,
                itemView.top,
                itemView.left + dX.toInt(),
                itemView.bottom
            )
            mDeleteBackground.draw(c)

            val deleteIconTop: Int = itemView.top + (itemHeight - mIntrinsicHeightDelete) / 2
            val deleteIconLeft: Int = itemView.left + itemView.width / 4 - mIntrinsicWidthDelete / 2
            val deleteIconRight: Int =
                itemView.left + itemView.width / 4 + mIntrinsicWidthDelete / 2
            val deleteIconBottom: Int = deleteIconTop + mIntrinsicHeightDelete

            mDeleteDrawable.setBounds(
                deleteIconLeft,
                deleteIconTop,
                deleteIconRight,
                deleteIconBottom
            )
            mDeleteDrawable.draw(c)
        } else {
            mPauseBackground.color = mPauseBackgroundColor
            mPauseBackground.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            mPauseBackground.draw(c)

            val pauseIconTop: Int = itemView.top + (itemHeight - mIntrinsicHeightPause) / 2
            val pauseIconLeft: Int = itemView.right - itemView.width / 4 - mIntrinsicWidthPause / 2
            val pauseIconRight: Int = itemView.right - itemView.width / 4 + mIntrinsicWidthPause / 2
            val pauseIconBottom: Int = pauseIconTop + mIntrinsicHeightPause
            mPauseDrawable.setBounds(pauseIconLeft, pauseIconTop, pauseIconRight, pauseIconBottom)
            mPauseDrawable.draw(c)
        }
    }

    private fun getTranslateX(itemView: View, dX: Float): Float {
        return if (dX >= 0) {
            min(dX, itemView.width / 2.toFloat())
        } else {
            max(dX, (-1) * itemView.width / 2.toFloat())
        }
    }

}