package my.lovely.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "basket_data_table")
data class Basket(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dish_id")
    var id: Int,
    @ColumnInfo(name = "dish_name")
    var name: String,
    @ColumnInfo(name = "dish_price")
    var price: Int,
    @ColumnInfo(name = "dish_weight")
    var weight: Int,
    @ColumnInfo(name = "dish_count")
    var count: Int,
    @ColumnInfo(name = "dish_image")
    var image: String
)
