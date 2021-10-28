package ru.fefu.lesson6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class BaseFragment<V : ViewBinding>(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    protected val binding: V
        get() = _binding!!

    private var _binding: V? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
            ?.also { _binding = provideViewBinding(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun provideViewBinding(view: View): V {
        val bindingType = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val bindingClass = bindingType as Class<V>
        val method = bindingClass.getMethod("bind", View::class.java)
        return method.invoke(null, view) as V
    }


}