package ru.fefu.lesson7

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ActivityFlowFragment : Fragment(R.layout.fragment_flow), FlowFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().apply {
                add(R.id.container, ActivityTabsFragment())
                commit()
            }
        }
    }

    override fun getFlowFragmentManager(): FragmentManager = childFragmentManager

}