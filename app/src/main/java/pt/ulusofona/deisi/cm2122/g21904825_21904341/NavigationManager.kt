package pt.ulusofona.deisi.cm2122.g21904825_21904341

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object NavigationManager {
    private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToDashBoard(fm: FragmentManager) {
        placeFragment(fm, DashboardFragment())
    }

    fun goToMap(fm: FragmentManager) {
        placeFragment(fm, MapFragment())
    }

    fun goToList(fm: FragmentManager) {
        placeFragment(fm, ListFragment())
    }

    fun goToRegister(fm: FragmentManager) {
        placeFragment(fm, RegisterFragment())
    }

    fun goToInfo(fm: FragmentManager) {
        placeFragment(fm, InfoFragment())
    }

    fun goToDetails(fm: FragmentManager) {
        placeFragment(fm, DetailsFragment())
    }

}