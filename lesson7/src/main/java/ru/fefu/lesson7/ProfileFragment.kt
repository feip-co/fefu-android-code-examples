package ru.fefu.lesson7

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    companion object {
        private const val EXTRA_ACTIVITY_ID = "activity_id"
        private const val EXTRA_IS_MINE = "is_mine"
        fun newInstance(activityId: Int, isMine: Boolean) = ProfileFragment().apply {
            arguments = bundleOf(EXTRA_IS_MINE to isMine, EXTRA_ACTIVITY_ID to activityId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*
        inflate menu example (for activity_details_screen)

        toolbar.inflateMenu(R.menu.my_activity)

        if (requireArguments().getBoolean(EXTRA_IS_MINE)) {
            toolbar.inflateMenu(R.menu.my_activity)
        }

        toolbar.menu.findItem(R.id.delete).setOnMenuItemClickListener {
            //todo
            true
        }*/
    }
}