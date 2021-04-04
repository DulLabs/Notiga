package com.dullabs.notiga.models

import com.dullabs.notiga.types.SectionType
import kotlin.properties.Delegates

class Section(val firstPosition: Int, val title: String, val sectionType: SectionType) {
    var sectionedPosition by Delegates.notNull<Int>()
}