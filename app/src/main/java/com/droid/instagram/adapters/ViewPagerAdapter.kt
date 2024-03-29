package com.droid.instagram.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter

//import androidx.fragment.app.FragmentManager


class ViewPagerAdapter (fm: androidx.fragment.app.FragmentManager): FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragmentList= mutableListOf<Fragment>()
    val TitleList= mutableListOf<String>()
    override fun getCount(): Int {
       return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {

        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TitleList.get(position)
    }
    fun addFragments(fragment: Fragment,title : String)
    {
        fragmentList.add(fragment)
        TitleList.add(title)
    }
}