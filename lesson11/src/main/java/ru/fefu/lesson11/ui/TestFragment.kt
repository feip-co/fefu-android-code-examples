package ru.fefu.lesson11.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ru.fefu.lesson11.databinding.FragmentCoilTestBinding
import ru.fefu.lesson11.databinding.FragmentTestBinding
import ru.fefu.lesson11.presentation.TestViewModel
import ru.fefu.lesson11.presentation.TrafficLightsViewModel

class TestFragment : Fragment() {

    private val viewModel: TestViewModel by lazy {
        ViewModelProvider(this)[TestViewModel::class.java]
    }

    private lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)

        viewModel.showLoader.observe(viewLifecycleOwner) {
            binding.pbLoader.isVisible = it
        }

        viewModel.data.observe(viewLifecycleOwner) {
            binding.tvData.text = it.map { it.toString() + "\n" }.toString()
        }

        viewModel.showError.observe(viewLifecycleOwner) {
            binding.tvError.isVisible = it.first
            binding.tvError.text = it.second
        }

        return binding.root
    }

}