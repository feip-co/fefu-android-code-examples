package ru.fefu.lesson11.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.fefu.lesson11.presentation.TrafficLightsViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.fefu.lesson11.R
import ru.fefu.lesson11.databinding.FragmentTrafficLightBinding

class TrafficLightsFragment : Fragment() {

    private val viewModel: TrafficLightsViewModel by lazy {
        ViewModelProvider(this)[TrafficLightsViewModel::class.java]
    }

    private lateinit var binding: ViewBinding

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrafficLightBinding.inflate(inflater, container, false)

        viewModel.randomLineColor.observe(viewLifecycleOwner) {
            binding.firstLight.setBackgroundColor(Color.parseColor(it.first))
            binding.secondLight.setBackgroundColor(Color.parseColor(it.second))
            binding.thirdLight.setBackgroundColor(Color.parseColor(it.third))
        }

        viewModel.timerValue.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it.toString()
        }

        viewModel.showTimer.observe(viewLifecycleOwner) {
            binding.tvTimer.isVisible = it
        }

        viewModel.showLoader.observe(viewLifecycleOwner) {
            binding.pbLoader.isVisible = it
        }

        viewModel.randomColor.observe(viewLifecycleOwner) {
            binding.randomLight.setBackgroundColor(Color.parseColor(it))
        }

        binding.firstLight.setOnClickListener { viewModel.onColorItemClicked(0) }
        binding.secondLight.setOnClickListener { viewModel.onColorItemClicked(1) }
        binding.thirdLight.setOnClickListener { viewModel.onColorItemClicked(2) }

        return binding.root
    }

}