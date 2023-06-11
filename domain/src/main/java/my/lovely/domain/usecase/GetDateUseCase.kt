package my.lovely.domain.usecase

import java.util.*

class GetDateUseCase() {

    fun getDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return String.format("%02d.%02d.%d", day, month, year)
    }

}