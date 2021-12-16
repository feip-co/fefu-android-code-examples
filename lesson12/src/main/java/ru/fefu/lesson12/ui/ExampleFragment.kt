package ru.fefu.lesson12.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.onEach
import ru.fefu.lesson12.launchWhenStarted
import ru.fefu.lesson12.databinding.FragmentExampleBinding
import ru.fefu.lesson12.presentation.ExampleViewModel

class ExampleFragment : Fragment() {

    private val viewModel: ExampleViewModel by lazy {
        ViewModelProvider(this)[ExampleViewModel::class.java]
    }

    private lateinit var binding: FragmentExampleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExampleBinding.inflate(inflater, container, false)

        with(binding) {
            viewModel.dataFlow
                .onEach { binding.tvData.text = it }
                .launchWhenStarted(viewLifecycleOwner.lifecycleScope)

            viewModel.loaderVisible
                .onEach { showLoader(it) }
                .launchWhenStarted(viewLifecycleOwner.lifecycleScope)

            rbFemale.isChecked = true

            binding.btnSave.setOnClickListener {
                viewModel.onSaveButtonClicked(
                    etLogin.text.toString(),
                    etPassword.text.toString(),
                    etFirstName.text.toString(),
                    if (rbFemale.isChecked) 1 else 0
                )
            }
        }

        return binding.root
    }

    fun showLoader(show: Boolean) {
        binding.pbLoader.isVisible = show
    }

}