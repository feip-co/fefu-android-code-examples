package ru.fefu.lesson7

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import ru.fefu.lesson7.databinding.FragmentActivityTabsBinding

class ActivityTabsFragment : BaseFragment<FragmentActivityTabsBinding>(R.layout.fragment_activity_tabs), FlowFragment {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = Adapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "Position $position"
        }.attach()
    }

    class Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = ActivitiesFragment()
    }

    override fun getFlowFragmentManager(): FragmentManager = (parentFragment as FlowFragment).getFlowFragmentManager()
}