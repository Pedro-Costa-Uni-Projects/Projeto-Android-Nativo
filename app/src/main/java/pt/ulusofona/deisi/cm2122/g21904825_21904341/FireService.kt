package pt.ulusofona.deisi.cm2122.g21904825_21904341

import pt.ulusofona.deisi.cm2122.g21904825_21904341.apiModels.ActiveFiresData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class GetFiresResponse(val success : Boolean, val data : List<ActiveFiresData>)

data class GetRisk(val success: Boolean, val data: String)

interface FireService {

    @GET("new/fires")
    suspend fun getAllFires() : GetFiresResponse

    @GET("v1/risk")
    suspend fun getRisk(
        @Query("concelho") concelho: String
    ) : GetRisk

}
