package ort.tp3_login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> home_guia()
            1 -> addServicio()

            else -> home_guia()
        }
    }
    companion object{
        private const val TAB_COUNT = 2
    }
}