
package my.lovely.domain.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import my.lovely.domain.model.Basket

@Dao
interface BasketDao {

    @Insert
    suspend fun insertDish(item: Basket)

    @Update
    suspend fun updateDish(item: Basket)

    @Delete
    suspend fun deleteDish(item: Basket)

    @Query("SELECT * FROM basket_data_table")
    fun getAllDishes(): LiveData<List<Basket>>
}
