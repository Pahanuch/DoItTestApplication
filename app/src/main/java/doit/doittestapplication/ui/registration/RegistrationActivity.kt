package doit.doittestapplication.ui.registration

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import doit.doittestapplication.R

import kotlinx.android.synthetic.main.activity_registration.*
import android.content.Intent
import android.util.Patterns
import com.bumptech.glide.Glide
import doit.doittestapplication.ui.base.WithBakButtonBarActivity
import doit.doittestapplication.ui.main.MainActivity
import doit.doittestapplication.utils.AppUtil
import doit.doittestapplication.utils.PermissionManager
import org.jetbrains.anko.startActivity


class RegistrationActivity : WithBakButtonBarActivity(), RegistrationView {

    @InjectPresenter
    lateinit var presenter : RegistrationPresenter

    private var imageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }

        avatar.setOnClickListener {

            if (!PermissionManager.checkPermissionGranted(this, PermissionManager.REQUEST_STORAGE)) {

                PermissionManager.verifyStoragePermissions(this)
            } else {
                val i = Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(i, RESULT_LOAD_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == RESULT_LOAD_IMAGE) {
                if (data != null) {
                    imageFileUri = data.data

                    Glide.with(this)
                            .load(imageFileUri)
                            .into(avatar)

                } else {
                    return
                }
            }

        }
    }

    override fun registrationSuccess() {
        finishAffinity()
        startActivity<MainActivity>()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun attemptLogin() {

        email.error = null
        password.error = null

        val emailStr = email.text.toString()
        val usernameStr = username.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        } else if (!isAvatarLoaded()){
            cancel = true
            showError(getString(R.string.avatar_empty_error))
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            presenter.registration(AppUtil.parseStringIntoRequestBody(usernameStr), AppUtil.parseStringIntoRequestBody(emailStr),
                    AppUtil.parseStringIntoRequestBody(passwordStr), imageFileUri!!)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }

    private fun isAvatarLoaded(): Boolean {
        return imageFileUri != null
    }

    companion object {
        private const val RESULT_LOAD_IMAGE = 1
    }
}

