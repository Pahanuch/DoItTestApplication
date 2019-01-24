package doit.doittestapplication.ui.base

import android.os.Bundle

abstract class MainBarActivity : BaseActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}