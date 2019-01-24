package doit.doittestapplication.ui.base

import android.os.Bundle

abstract class WithBakButtonBarActivity : BaseActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}