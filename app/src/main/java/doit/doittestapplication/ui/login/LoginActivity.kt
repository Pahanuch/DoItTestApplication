package doit.doittestapplication.ui.login

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import doit.doittestapplication.R
import doit.doittestapplication.data.api.model.SignInRequestBody
import doit.doittestapplication.ui.base.BaseActivity
import doit.doittestapplication.ui.main.MainActivity
import doit.doittestapplication.ui.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            val emailStr = email.text.toString()
            val passwordStr = password.text.toString()
            presenter.login(SignInRequestBody(emailStr, passwordStr))
        }

        registerButton.setOnClickListener {
            startActivity<RegistrationActivity>()
        }
    }

    override fun showMainScreen() {
        finish()
        startActivity<MainActivity>()
    }
}