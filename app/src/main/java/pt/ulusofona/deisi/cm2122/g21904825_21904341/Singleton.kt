package pt.ulusofona.deisi.cm2122.g21904825_21904341

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.codec.binary.Base64
import javax.security.auth.callback.Callback

object Singleton {
    var dao = contextStatic?.let { FireDatabase.getInstance(it.applicationContext).fireDao() }

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

    fun add(fire : Fire) {
        fires.add(0, fire)
    }

    fun getList() : ArrayList<Fire> {
        CoroutineScope(Dispatchers.IO).launch {
            val firesDao = dao?.getAll()
            fires = firesDao?.map {
                Fire(it.name, it.cc, it.district, it.timestamp, Base64.decodeBase64(it.photo))
            } as ArrayList<Fire>
        }
        return fires
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