package pt.ulusofona.deisi.cm2122.g21904825_21904341.data

import com.google.android.gms.maps.model.LatLng
import pt.ulusofona.deisi.cm2122.g21904825_21904341.R
import pt.ulusofona.deisi.cm2122.g21904825_21904341.resourcesStatic

object Districts {
    var listDistricts : ArrayList<String> = arrayListOf(
        resourcesStatic!!.getString(R.string.all),
        "Aveiro", "Beja", "Braga", "Bragança", "Castelo Branco",
        "Coimbra", "Évora", "Faro", "Guarda", "Leiria", "Lisboa",
        "Portalegre", "Porto", "Santarém", "Setúbal", "Viana do Castelo",
        "Vila Real", "Viseu"
    )
    var listCords: ArrayList<LatLng> = arrayListOf(
        LatLng(40.737778, -8.532731), //Aveiro
        LatLng(38.004602, -7.861997), //Beja
        LatLng(41.551649, -8.408321), //Braga
        LatLng(41.550351, -6.832636), //Bragança
        LatLng(39.836606, -7.493704), //Castelo Branco
        LatLng(40.187126, -8.407053), //Coimbra
        LatLng(38.555097, -7.912293), //Évora
        LatLng(37.187208, -8.151781), //Faro
        LatLng(40.557368, -7.243823), //Guarda
        LatLng(39.566453, -9.033903), //Leiria
        LatLng(39.026048, -9.201655), //Lisboa
        LatLng(39.221928, -7.607616), //Portalegre
        LatLng(41.222909, -8.322753), //Porto
        LatLng(39.353800, -8.463104), //Santarém
        LatLng(38.362837, -8.699229), //Setúbal
        LatLng(41.881028, -8.524518), //Viana do Castelo
        LatLng(41.529776, -7.623218), //Vila Real
        LatLng(40.810862, -7.898068), //Viseu
    )
}