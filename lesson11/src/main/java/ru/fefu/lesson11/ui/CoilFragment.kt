package ru.fefu.lesson11.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.fefu.lesson11.databinding.FragmentCoilTestBinding

class CoilFragment : Fragment() {

    private lateinit var binding: FragmentCoilTestBinding

    @ExperimentalCoilApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoilTestBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launch {
            binding.pbLoader.isVisible = true
            delay(1_000)
            Coil.enqueue(
                ImageRequest.Builder(this@CoilFragment.requireContext())
                    .data("https://vacinartmsk.files.wordpress.com/2020/11/rumdum-kurs-piton-dlya-zhurnalyug.png")
                    .target(binding.ivImage)
                    .build()
            ).await()
            binding.pbLoader.isVisible = false
        }
        return binding.root
    }

}