package ru.fefu.lesson6

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.fefu.lesson6.databinding.FragmentSharedPrefsBinding

class SharedPrefsFragment : BaseFragment<FragmentSharedPrefsBinding>(R.layout.fragment_shared_prefs) {

    companion object {
        private const val SHARED_PREFS = "example_shared_prefs"

        private const val KEY_SAVED_VALUE = "saved_value"
    }

    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveValue.setOnClickListener {
            sharedPrefs.edit().putString(KEY_SAVED_VALUE, binding.etInput.text.toString()).apply()
        }

        binding.btnReadValue.setOnClickListener {
            binding.tvSavedValue.text = sharedPrefs.getString(KEY_SAVED_VALUE, null)
        }
    }


}