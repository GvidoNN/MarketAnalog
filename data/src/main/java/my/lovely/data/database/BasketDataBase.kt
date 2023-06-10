package my.lovely.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import my.lovely.domain.database.BasketDao
import my.lovely.domain.model.Basket

@Database(entities = [Basket::class], version = 1, exportSchema = false)
abstract class BasketDataBase : RoomDatabase() {

    abstract fun BasketDao(): BasketDao

}