package ru.fefu.helloworld.navigation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.fefu.helloworld.BaseFragment
import ru.fefu.helloworld.R
import ru.fefu.helloworld.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<FragmentSecondBinding>(R.layout.fragment_second) {

    companion object {
        fun newInstance(userId: String) = SecondFragment().apply {
            arguments = bundleOf("user_id" to userId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCiceroneNavigation()

        requireArguments().getString("user_id")
    }

    private fun setupNavigation() {
        binding.toolbar.setupWithNavController(findNavController())
    }

    private fun setupCiceroneNavigation() {
        binding.toolbar.setNavigationOnClickListener {
            App.INSTANCE.router.exit()
        }
    }

}