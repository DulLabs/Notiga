package com.dullabs.notiga.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.dullabs.notiga.R
import com.dullabs.notiga.models.NotificationWrapper
import com.dullabs.notiga.types.SectionType
import kotlin.properties.Delegates

class NotificationWrapperSectionedRecyclerViewAdapter(
    private var mNotificationsWrapperData: List<NotificationWrapper>,
    private var mContext: Context,
    private val onNotificationWrapperClicked: (NotificationWrapper) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SECTION_TYPE = 0
        const val NOTIFICATION_WRAPPER_TYPE = 1
    }

    private val mSectionTypeToSectionedPosition: HashMap<SectionType, Int> = HashMap()

    private val mSectionTypeToSection: HashMap<SectionType, Section> = HashMap()

    inner class NotificationWrapperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mIconImage = itemView.findViewById<ImageView>(R.id.notificationWrapperIconImage)
        private val mAppName = itemView.findViewById<TextView>(R.id.notificationWrapperAppName)
        private val mNotificationDescription = itemView.findViewById<TextView>(R.id.notificationWrapperDescription)
        private val mNotificationWrapperCount = itemView.findViewById<TextView>(R.id.notificationWrapperCount)

        init {
            itemView.setOnClickListener {
                // TODO Change adapter position
                onNotificationWrapperClicked(mNotificationsWrapperData[sectionedPositionToPosition(adapterPosition)])
            }
        }

        fun bindTo(currentNotificationWrapper: NotificationWrapper) {
            mIconImage.setImageResource(currentNotificationWrapper.getLastNotification().getAppIconId())
            mAppName.text = currentNotificationWrapper.getLastNotification().getAppName()
            mNotificationDescription.text = currentNotificationWrapper.getLastNotification().getNotificationDescription()
            val count = "+${currentNotificationWrapper.getCount()}"
            mNotificationWrapperCount.text = count
        }
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mSectionText = itemView.findViewById<TextView>(R.id.sectionText)

        fun bindTo(title: String) {
            mSectionText.text = title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == SECTION_TYPE) {
            SectionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.section, parent, false))
        } else {
            NotificationWrapperViewHolder(LayoutInflater.from(mContext).inflate(R.layout.notification_wrapper_item, parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, sectionedPosition: Int) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            mSectionTypeToSection[getSectionTypeFromSectionedPosition(sectionedPosition)]?.title?.let {
                (holder as SectionViewHolder).bindTo(
                    it
                )
            }
        } else {
            (holder as NotificationWrapperViewHolder).bindTo(mNotificationsWrapperData[sectionedPositionToPosition(sectionedPosition)])
        }
    }

    override fun getItemViewType(sectionedPosition: Int): Int {
        return if (isSectionHeaderPosition(sectionedPosition)) SECTION_TYPE else NOTIFICATION_WRAPPER_TYPE
    }

    class Section(val firstPosition: Int, val title: String, val sectionType: SectionType) {
        var sectionedPosition by Delegates.notNull<Int>()
    }

    fun setSections(@NonNull sections: List<Section>) {
        mSectionTypeToSectionedPosition.clear()
        mSectionTypeToSection.clear()

        // sort the sections according to their first positions
        // sections in the main code can be added in arbitrary fashion (not necessarily sorted)
        val sortedSections = sections.sortedWith(Comparator { o, o1 -> if (o.firstPosition == o1.firstPosition) 0 else if (o.firstPosition < o1.firstPosition) -1 else 1 })

        for ((offset, section) in sortedSections.withIndex()) {
            section.sectionedPosition = section.firstPosition + offset
            mSectionTypeToSectionedPosition[section.sectionType] = section.sectionedPosition
            mSectionTypeToSection[section.sectionType] = section
        }
        notifyDataSetChanged()
    }

//    fun positionToSectionedPosition(position: Int): Int {
//        var offset = 0
//        for (i in 0 until mSections.size()) {
//            if (mSections.valueAt(i).firstPosition > position) {
//                break
//            }
//            ++offset
//        }
//        return position + offset
//    }

    fun sectionedPositionToPosition(sectionedPosition: Int): Int {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION
        }
        var offset = 0
        for (secPos in mSectionTypeToSectionedPosition.values) {
            if (secPos < sectionedPosition) {
                --offset
            }
        }
        return sectionedPosition + offset
    }

    private fun isSectionHeaderPosition(sectionedPosition: Int): Boolean {
        return mSectionTypeToSectionedPosition.filterValues { it == sectionedPosition }.keys.isNotEmpty()
    }

    override fun getItemCount(): Int {
        return if (mNotificationsWrapperData.isNotEmpty()) mSectionTypeToSectionedPosition.size + mNotificationsWrapperData.size else 0
    }

    fun getNotificationWrapperItemAtPosition(position: Int): NotificationWrapper {
        return mNotificationsWrapperData[position]
    }

    private fun getSectionTypeFromSectionedPosition(sectionedPosition: Int): SectionType {
        return mSectionTypeToSectionedPosition.filterValues { it == sectionedPosition }.keys.elementAt(0)
    }

    fun removeItemFromSectionedPosition(sectionedPosition: Int) {
        for (key in mSectionTypeToSectionedPosition.keys) {
            if (mSectionTypeToSectionedPosition[key]!! > sectionedPosition) {
                val oldPosition = mSectionTypeToSectionedPosition[key]
                if (oldPosition != null) {
                    mSectionTypeToSectionedPosition[key] = oldPosition-1
                }
            }
        }
    }

    fun restoreItemToSectionedPosition(sectionedPosition: Int) {
        for (key in mSectionTypeToSectionedPosition.keys) {
            if (mSectionTypeToSectionedPosition[key]!! >= sectionedPosition) {
                val oldPosition = mSectionTypeToSectionedPosition[key]
                if (oldPosition != null) {
                    mSectionTypeToSectionedPosition[key] = oldPosition+1
                }
            }
        }
    }

}