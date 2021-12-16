package ru.fefu.lesson6.sqlite

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.lesson6.BaseFragment
import ru.fefu.lesson6.R
import ru.fefu.lesson6.databinding.FragmentRoomBinding
import ru.fefu.lesson6.room.adapter.CatsAdapter
import ru.fefu.lesson6.room.db.Cat

class SqliteFragment : BaseFragment<FragmentRoomBinding>(R.layout.fragment_room) {

    private val dbHelper by lazy {
        SqliteHelper(requireContext(), "database")
    }

    private val catsAdapter by lazy {
        CatsAdapter {
            dbHelper.deleteCat(it.id)
            updateList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catsAdapter.submitList(dbHelper.getAllCats())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rvCats) {
            adapter = catsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.btnAdd.setOnClickListener {
            dbHelper.insertCat(
                Cat(
                    0,
                    "Cat number ${System.currentTimeMillis()}",
                    System.currentTimeMillis()
                )
            )
            updateList()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun updateList() {
        catsAdapter.submitList(dbHelper.getAllCats())
    }

}