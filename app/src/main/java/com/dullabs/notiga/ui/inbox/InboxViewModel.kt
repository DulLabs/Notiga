package com.dullabs.notiga.ui.inbox

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dullabs.notiga.adapters.NotificationWrapperSectionedRecyclerViewAdapter
import com.dullabs.notiga.models.Notification
import com.dullabs.notiga.models.NotificationWrapper
import com.dullabs.notiga.models.Section
import com.dullabs.notiga.repositories.NotificationRepository
import com.dullabs.notiga.repositories.NotificationWrapperRepository
import com.dullabs.notiga.types.SectionType

class InboxViewModel : ViewModel() {

    private lateinit var _mNotificationsWrapper: MutableLiveData<ArrayList<NotificationWrapper>>
    private var _mSectionTypeToSectionedPosition: MutableLiveData<HashMap<SectionType, Int>> = MutableLiveData()
    private var _mSectionTypeToSection: MutableLiveData<HashMap<SectionType, Section>> =  MutableLiveData()
    private lateinit var mRepo: NotificationWrapperRepository

    fun getNotificationsWrapper(): LiveData<ArrayList<NotificationWrapper>> {
        return _mNotificationsWrapper
    }

    fun getSectionTypeToSectionedPosition(): LiveData<HashMap<SectionType, Int>> {
        return _mSectionTypeToSectionedPosition
    }

    fun getSectionTypeToSection(): LiveData<HashMap<SectionType, Section>> {
        return _mSectionTypeToSection
    }

    fun init() {
        if (this::_mNotificationsWrapper.isInitialized) {
            return
        }
        mRepo = NotificationWrapperRepository.getInstance()
        _mNotificationsWrapper = mRepo.getNotificationsWrapper()
        //This is the code to provide a sectioned list
        val sections: ArrayList<Section> = ArrayList()
        //Sections
        sections.add(Section(0, "Section 1", SectionType.TodaySection))
        sections.add(Section(5, "Section 2", SectionType.YesterdaySection))
        sections.add(Section(12, "Section 3", SectionType.OlderSection))
        setSections(sections)
        println("Initialized notifications in view, size is ${getNotificationsWrapper().value!!.size}")
    }

    fun removeNotification(sectionedPosition: Int, position: Int) {
        // This part updates data base or api call
//        mRepo.removeNotification(sectionedPosition)

        val currentNotifications: ArrayList<NotificationWrapper> = _mNotificationsWrapper.value!!
        currentNotifications.removeAt(position)
        removeItemFromSectionedPosition(sectionedPosition)
        _mNotificationsWrapper.postValue(currentNotifications)
    }

    fun restoreNotification(sectionedPosition: Int, position: Int, notificationWrapper: NotificationWrapper) {
        // This part updates data base or api call
//        mRepo.restoreNotification(sectionedPosition, notification)

        val currentNotifications: ArrayList<NotificationWrapper> = _mNotificationsWrapper.value!!
        currentNotifications.add(position, notificationWrapper)
        restoreItemToSectionedPosition(sectionedPosition)
        _mNotificationsWrapper.postValue(currentNotifications)
    }

    private fun setSections(@NonNull sections: List<Section>) {
        // sort the sections according to their first sectionedPositions
        // sections in the main code can be added in arbitrary fashion (not necessarily sorted)
        val sortedSections = sections.sortedWith(Comparator { o, o1 -> if (o.firstPosition == o1.firstPosition) 0 else if (o.firstPosition < o1.firstPosition) -1 else 1 })
        val sectionTypeToSecPos: HashMap<SectionType, Int> = HashMap()
        val sectionTypeToSec: HashMap<SectionType, Section> = HashMap()
        for ((offset, section) in sortedSections.withIndex()) {
            section.sectionedPosition = section.firstPosition + offset
            sectionTypeToSecPos[section.sectionType] = section.sectionedPosition
            sectionTypeToSec[section.sectionType] = section
        }
        _mSectionTypeToSectionedPosition.value = sectionTypeToSecPos
        _mSectionTypeToSection.value = sectionTypeToSec
    }

    private fun removeItemFromSectionedPosition(sectionedPosition: Int) {
        for (key in _mSectionTypeToSectionedPosition.value?.keys!!) {
            if (_mSectionTypeToSectionedPosition.value!![key]!! > sectionedPosition) {
                val oldPosition = _mSectionTypeToSectionedPosition.value!![key]
                if (oldPosition != null) {
                    _mSectionTypeToSectionedPosition.value!![key] = oldPosition-1
                }
            }
        }
    }

    private fun restoreItemToSectionedPosition(sectionedPosition: Int) {
        for (key in _mSectionTypeToSectionedPosition.value?.keys!!) {
            if (_mSectionTypeToSectionedPosition.value!![key]!! >= sectionedPosition) {
                val oldPosition = _mSectionTypeToSectionedPosition.value!![key]
                if (oldPosition != null) {
                    _mSectionTypeToSectionedPosition.value!![key] = oldPosition+1
                }
            }
        }
    }

}