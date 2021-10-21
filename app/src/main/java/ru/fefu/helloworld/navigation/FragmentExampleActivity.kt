package ru.fefu.helloworld.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.fefu.helloworld.R
import ru.fefu.helloworld.databinding.ActivityFragmentExampleBinding

class FragmentExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentExampleBinding

    private val navigator by lazy { AppNavigator(this, R.id.fragmentContainer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFragmentExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        App.INSTANCE.router.newRootScreen(Screens.firstScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        App.INSTANCE.router.exit()
    }

}