package ru.fefu.lesson6.room

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.lesson6.App
import ru.fefu.lesson6.BaseFragment
import ru.fefu.lesson6.R
import ru.fefu.lesson6.databinding.FragmentRoomBinding
import ru.fefu.lesson6.room.adapter.CatsAdapter
import ru.fefu.lesson6.room.db.Cat

class RoomFragment : BaseFragment<FragmentRoomBinding>(R.layout.fragment_room) {

    private val catsAdapter by lazy {
        CatsAdapter { App.INSTANCE.db.catDao().delete(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvCats) {
            adapter = catsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        App.INSTANCE.db.catDao().getAll().observe(viewLifecycleOwner) {
            catsAdapter.submitList(it)
        }

        binding.btnAdd.setOnClickListener {
            App.INSTANCE.db.catDao().insert(
                Cat(
                    0,
                    "Cat number ${System.currentTimeMillis()}",
                    System.currentTimeMillis()
                )
            )
        }
    }

}