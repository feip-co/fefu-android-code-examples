package ru.fefu.lesson7

import android.os.Bundle
import androidx.fragment.app.Fragment

class ProfileFlowFragment : Fragment(R.layout.fragment_flow) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().apply {
                add(R.id.container, ProfileFragment())
                commit()
            }
        }
    }


}