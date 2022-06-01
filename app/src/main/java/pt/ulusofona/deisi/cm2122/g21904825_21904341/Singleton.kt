package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.codec.binary.Base64
import retrofit2.HttpException

object Singleton {
    private val TAG = Singleton::class.java.simpleName
    var dao = contextStatic?.let { FireDatabase.getInstance(it.applicationContext).fireDao() }
    private var retrofit = RetrofitBuilder.getInstance("https://api-dev.fogos.pt/")
    private var service = retrofit.create(FireService::class.java)

    private var fires : ArrayList<Fire> = ArrayList()
    private var district : String = " "
    private var county : String = " "
    var risco = ""

    fun getList(updateAdapter: (() -> Unit)) : ArrayList<Fire> {
        CoroutineScope(Dispatchers.IO).launch {
            val firesDao = dao?.getAll()
            fires = firesDao?.map {
                if (it.name != "NaN") { //Fogo resgistado pelo utilizador
                    if (it.photo != null) {
                        //<3 Dedicado ao Bernardo <3//
                        Fire(it.name, it.cc, it.district, it.county, it.timestamp, Base64.decodeBase64(it.photo.toByteArray()), it.latitude, it.longitude)
                    } else {
                        Fire(it.name, it.cc, it.district, it.county, it.timestamp,null, it.latitude, it.longitude)
                    }
                } else { //Fogo da API Fogos.pt
                    Fire(it.district, it.county, it.parish, it.operational, it.vehicles, it.aerial, it.state, it.timestamp, it.comments, it.latitude, it.longitude)
                }
            } as ArrayList<Fire>
            updateAdapter()
        }
        return fires
    }

    fun getFromAPIaddToLocalDB() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val firesAPI = service.getAllFires().data
                firesAPI.map {
                    val fire = FireRoom(true,"NaN", 0, it.district, it.concelho, it.freguesia, it.man, it.terrain, it.aerial, it.status, (it.dateTime.sec).toLong() * 1000, "${resourcesStatic!!.getString(R.string.nature_fire)} : ${it.natureza}",null, it.lat, it.lng)
                    dao?.insert(fire)
                }
            }catch (ex: HttpException) {
                Log.e(TAG, ex.message())
            }
        }
    }

    fun getRisk(update: (() -> Unit)) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                risco = service.getRisk(county).data
            }catch (ex: HttpException) {
                Log.e(TAG, ex.message())
            }
            update()
        }
    }

    fun activeFires(update: ((Int) -> Unit)) {
        update(fires.size)
    }

    fun activeDistrictAndCounty(option : String, update: ((Int) -> Unit)) {
        var activeDistrict = 0
        var activeCounty = 0
        for (fire in fires) {
            if (fire.getDistrict() == district) {
                activeDistrict++
            }
            if (fire.getCounty() == county) {
                activeCounty++
            }
        }

        if (option == "d") {
            update(activeDistrict)
        } else if (option == "m") {
            update(activeCounty)
        }

    }

    fun getDistrict(update: ((String) -> Unit)) {
        update(district)
    }

    fun getCounty(update: ((String) -> Unit)) {
        update(county)
    }

    fun setDistrict(district : String) {
        this.district = district
    }

    fun setCounty(county : String) {
        this.county = county
    }
}