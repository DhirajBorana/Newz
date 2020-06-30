package com.example.newz.ui.section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.newz.R
import com.example.newz.ui.section.SectionType.*

object SectionModel {

    private var sectionItems = mutableListOf(
        Section(FrontPage, R.string.front_page, true),
        Section(World, R.string.world, false),
        Section(Politics, R.string.politics, false),
        Section(Business, R.string.business, false),
        Section(Technology, R.string.technology, false),
        Section(Science, R.string.science, false),
        Section(Health, R.string.health, false),
        Section(Sports, R.string.sports, false),
        Section(Arts, R.string.arts, false),
        Section(Books, R.string.books, false),
        Section(Fashion, R.string.fashion, false),
        Section(Food, R.string.food, false),
        Section(Travel, R.string.travel, false),
        Section(Movies, R.string.movies, false),
        Section(Automobiles, R.string.automobiles, false),
        Section(RealEstate, R.string.real_estate, false),
        Section(SundayReview, R.string.sunday_review, false)
    )

    private val _sectionList = MutableLiveData<List<Section>>()
    val sectionList: LiveData<List<Section>>
        get() = _sectionList

    val checked: LiveData<Section> = _sectionList.map { list ->
        list.first { it.isChecked }
    }

    init {
        postListUpdate()
    }

    fun setSectionItemChecked(type: SectionType): Boolean {
        var updated = false
        sectionItems.forEachIndexed { index, section ->
            val shouldCheck = section.type == type
            if (section.isChecked != shouldCheck) {
                sectionItems[index] = section.copy(isChecked = shouldCheck)
                updated = true
            }
        }
        if (updated) postListUpdate()
        return updated
    }

    private fun postListUpdate() {
        _sectionList.value = sectionItems
    }
}

data class Section(
    val type: SectionType,
    val titleRes: Int,
    val isChecked: Boolean
)

sealed class SectionType {
    object FrontPage : SectionType()
    object Arts : SectionType()
    object Automobiles : SectionType()
    object Books : SectionType()
    object Business : SectionType()
    object Fashion : SectionType()
    object Food : SectionType()
    object Health : SectionType()
    object Movies : SectionType()
    object Politics : SectionType()
    object RealEstate : SectionType()
    object Science : SectionType()
    object Sports : SectionType()
    object SundayReview : SectionType()
    object Technology : SectionType()
    object Travel : SectionType()
    object World : SectionType()
}