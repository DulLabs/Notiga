package com.dullabs.notiga.types

sealed class SectionType {
    object TodaySection : SectionType()
    object YesterdaySection : SectionType()
    object OlderSection : SectionType()
}