@file:Suppress("DEPRECATION")

package doit.doittestapplication.ui.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import doit.doittestapplication.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.indeterminateProgressDialog

@Suppress("DEPRECATION")
@SuppressLint("Registered")
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    private var dialog: ProgressDialog? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showNetworkError() {
        error(getString(R.string.network_error))
    }

    override fun showUnknownError() {
        error(getString(R.string.uknown_error))
    }

    override fun showError(message: String) {
        error(message)
    }

    override fun showMessage(message: String) {
        message(message)
    }

    override fun showLoading() {
        dialog?.dismiss()
        dialog = indeterminateProgressDialog(message = getString(R.string.loading_text))
        dialog?.setCancelable(false)
        dialog?.show()
    }

    override fun hideLoading() {
        dialog?.dismiss()
    }

    private fun error(message: String) {
        alert(factory = Appcompat, message = message, title = getString(R.string.error_title)) { positiveButton(android.R.string.ok) {} }.show()
    }

    private fun message(message: String) {
        alert(factory = Appcompat, message = message, title = getString(R.string.message_title)) { positiveButton(android.R.string.ok) {} }.show()
    }

}