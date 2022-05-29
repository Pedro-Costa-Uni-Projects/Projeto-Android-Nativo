package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.codec.binary.Base64
import retrofit2.HttpException
import javax.security.auth.callback.Callback

object Singleton {
    private val TAG = Singleton::class.java.simpleName
    var dao = contextStatic?.let { FireDatabase.getInstance(it.applicationContext).fireDao() }
    private var retrofit = RetrofitBuilder.getInstance("https://api-dev.fogos.pt/")
    private var service = retrofit.create(FireService::class.java)

    private var listDistricts : ArrayList<String> = arrayListOf(
        resourcesStatic!!.getString(R.string.district_hint_form),
        "Aveiro",
        "Beja",
        "Braga",
        "Bragança",
        "Castelo Branco",
        "Coimbra",
        "Évora",
        "Faro",
        "Guarda",
        "Leiria",
        "Lisboa",
        "Portalegre",
        "Porto",
        "Santarém",
        "Setúbal",
        "Viana do Castelo",
        "Vila Real",
        "Viseu"
    )

    private var fires : ArrayList<Fire> = ArrayList()
    private var district : String = "Setúbal" //Pre preenchido
    private var county : String = "Barreiro" //Pre preenchido

    fun getListDistricts() : ArrayList<String> {
        return this.listDistricts
    }

    fun getList(updateAdapter: (() -> Unit)) : ArrayList<Fire> {
        CoroutineScope(Dispatchers.IO).launch {
            val firesDao = dao?.getAll()
            fires = firesDao?.map {
                if (it.name != "NaN") { //Fogo resgistado pelo utilizador
                    Fire(it.name, it.cc, it.district, it.timestamp, Base64.decodeBase64(it.photo))
                } else { //Fogo da API Fogos.pt
                    Fire(it.district, it.county, it.parish, it.operational, it.vehicles, it.aerial, it.state, it.timestamp, it.comments)
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
                    val fire = FireRoom("NaN", 0, it.district, it.concelho, it.freguesia, it.man, it.terrain, it.aerial, it.status, (it.dateTime.sec).toLong() * 1000, "${resourcesStatic!!.getString(R.string.nature_fire)} : ${it.natureza}",null)
                    dao?.insert(fire)
                }
            }catch (ex: HttpException) {
                Log.e(TAG, ex.message())
            }
        }
    }

    fun activeFires() : Int {
        return fires.size
    }

    fun activeDistrictAndCounty(option : String) : Int {
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
            return activeDistrict
        } else if (option == "m") {
            return  activeCounty
        }

        return 0
    }

    fun getDistrict() : String {
        return district
    }

    fun getCounty() : String {
        return county
    }

    fun getAllFires(onFinished: (ArrayList<Fire>)) {

    }
}