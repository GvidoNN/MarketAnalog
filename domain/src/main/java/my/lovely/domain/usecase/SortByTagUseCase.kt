package my.lovely.domain.usecase

import my.lovely.domain.model.Dishe

class SortByTagUseCase {

    fun sort(dishesList: ArrayList<Dishe>, tag: String): ArrayList<Dishe> {
        return dishesList.filter { it.tegs.contains(tag) } as ArrayList<Dishe>
    }
}