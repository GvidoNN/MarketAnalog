package my.lovely.domain.model

import com.google.gson.annotations.SerializedName
import my.lovely.domain.model.Catalog

data class DataResponse(
    @SerializedName("сategories")
    val catalog: List<Catalog>
)