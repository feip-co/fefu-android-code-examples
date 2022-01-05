package co.feip.lesson13.mvp

interface LoginView {

    fun showLoading(show: Boolean)

    fun showLoginError()

    fun showPasswordError()

    fun showResult(result: String)

}