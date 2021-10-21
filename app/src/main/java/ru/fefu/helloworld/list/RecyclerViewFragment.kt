package ru.fefu.helloworld.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.helloworld.BaseFragment
import ru.fefu.helloworld.R
import ru.fefu.helloworld.databinding.FragmentRecyclerViewBinding

class RecyclerViewFragment :
    BaseFragment<FragmentRecyclerViewBinding>(R.layout.fragment_recycler_view) {

    private val catsRepository = CatsRepository()

    private val exampleAdapter = ExampleAdapter(catsRepository.getCats())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            adapter = exampleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        exampleAdapter.setItemClickListener { exampleAdapter.removeCat(it) }

//        binding.btnAdd.setOnClickListener { exampleAdapter.addCat(catsRepository.getRandomCat()) }
    }

}