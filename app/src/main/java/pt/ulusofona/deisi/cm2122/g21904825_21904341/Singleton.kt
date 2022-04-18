package pt.ulusofona.deisi.cm2122.g21904825_21904341

object Singleton {
    private var fires : ArrayList<Fire> = ArrayList()
    private var district : String = "Setúbal"
    private var municipality : String = "Barreiro"

    fun add(fire : Fire) {
        fires.add(fire)
    }

    fun getList() : ArrayList<Fire> {
        return fires
    }

    fun newList(list : ArrayList<Fire>) {
        fires = list
    }

    fun activeFires() : Int {
        return fires.size
    }

    fun activeDistrictandMunicipality(option : String) : Int {
        var activeDistrict = 0
        var activeMunicipality = 0
        for (fire in fires) {
            if (fire.distrito == district) {
                activeDistrict++
            }
            if (fire.concelho == municipality) {
                activeMunicipality++
            }
        }

        if (option == "d") {
            return activeDistrict
        } else if (option == "m") {
            return  activeMunicipality
        }

        return 0
    }

    fun getDistrict() : String {
        return district
    }

    fun getMunicipality() : String {
        return municipality
    }

}