package my.lovely.marketanalog.domain.model

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("сategories")
    val catalog: List<Catalog>
)