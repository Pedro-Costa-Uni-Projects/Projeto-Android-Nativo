package pt.ulusofona.deisi.cm2122.g21904825_21904341

import pt.ulusofona.deisi.cm2122.g21904825_21904341.apiModels.ActiveFiresData
import retrofit2.http.GET

data class GetFiresResponse(val success : Boolean, val data : List<ActiveFiresData>)

interface FireService {

    @GET("new/fires")
    suspend fun getAllFires() : GetFiresResponse
}