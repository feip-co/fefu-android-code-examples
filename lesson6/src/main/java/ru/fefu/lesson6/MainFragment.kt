package ru.fefu.lesson6

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.fefu.lesson6.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSharedPrefs.setOnClickListener { replaceFragment(SharedPrefsFragment()) }
        binding.btnAppSpecificStorage.setOnClickListener { replaceFragment(AppSpecificStorageFragment()) }

    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

}