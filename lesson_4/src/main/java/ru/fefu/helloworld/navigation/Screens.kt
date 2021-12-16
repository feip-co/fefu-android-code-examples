package ru.fefu.helloworld.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun firstScreen() = FragmentScreen { FirstFragment() }
    fun secondScreen(userId: String) = FragmentScreen { SecondFragment.newInstance(userId) }

    object FirstScreen : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment = FirstFragment()
    }

}