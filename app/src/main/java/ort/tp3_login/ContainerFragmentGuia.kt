package ort.tp3_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class containerFragmentGuia : Fragment() {
    lateinit var v: View
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_container_guia, container, false)
        tabLayout = v.findViewById(R.id.tab_layout_guia)
        viewPager = v.findViewById(R.id.viewpager_guia)
        return v
    }

    override fun onStart() {
        super.onStart()

        viewPager.setAdapter(ViewPagerAdapter(requireActivity()))

        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->

                when (position) {
                    0 -> {
                        tab.setIcon(R.drawable.icon_home)
                        tab.text = "Home"
                    }
                    1-> {
                        tab.setIcon(R.drawable.icon_add)
                        tab.text = "Add"
                    }
                    else-> tab.text = "undefined"
                }

            }).attach()



    }
}