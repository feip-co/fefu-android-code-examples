package ru.fefu.lesson6

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.fefu.lesson6.databinding.FragmentMainBinding
import ru.fefu.lesson6.room.RoomFragment

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSharedPrefs.setOnClickListener { replaceFragment(SharedPrefsFragment()) }
        binding.btnAppSpecificStorage.setOnClickListener { replaceFragment(AppSpecificStorageFragment()) }
        binding.btnRoom.setOnClickListener { replaceFragment(RoomFragment()) }
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

}