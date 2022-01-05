package co.feip.lesson13.mvp.moxy

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface LoginView : MvpView {

    @AddToEndSingle
    fun showLoading(show: Boolean)

    @OneExecution
    fun showLoginError()

    @OneExecution
    fun showPasswordError()

    @AddToEndSingle
    fun showResult(result: String)

}