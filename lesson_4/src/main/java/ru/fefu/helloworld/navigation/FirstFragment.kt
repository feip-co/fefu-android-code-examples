package ru.fefu.helloworld.navigation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import ru.fefu.helloworld.BaseFragment
import ru.fefu.helloworld.R
import ru.fefu.helloworld.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment<FragmentFirstBinding>(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCiceroneNavigation()
    }

    private fun setupNavigation() {
        binding.button.setOnClickListener {
            findNavController().navigate(
                R.id.action_firstFragment_to_secondFragment,
                bundleOf("user_id" to "some_user_id")
            )
        }
    }

    private fun setupSafeArgsNavigation() {
        binding.button.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment("some_user_id")
            findNavController().navigate(action)
        }
    }

    private fun setupCiceroneNavigation() {
        binding.button.setOnClickListener {
            App.INSTANCE.router.navigateTo(Screens.secondScreen("some_user_id"))
        }
    }

}