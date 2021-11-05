package ru.fefu.lesson7

import android.os.Bundle
import android.view.View
import ru.fefu.lesson7.databinding.FragmentActivityDetailsBinding

class ActivityDetailsFragment : BaseFragment<FragmentActivityDetailsBinding>(R.layout.fragment_activity_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            (parentFragment as FlowFragment).getFlowFragmentManager().popBackStack()
        }
    }

}