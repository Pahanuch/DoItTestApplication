package doit.doittestapplication.ui.main

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import doit.doittestapplication.R
import doit.doittestapplication.data.api.model.GifResponse
import doit.doittestapplication.data.api.model.PictureResponse
import doit.doittestapplication.data.storage.Storage
import doit.doittestapplication.ui.adapters.PicturesListAdapter
import doit.doittestapplication.ui.base.MainBarActivity
import doit.doittestapplication.ui.dialogs.PictureDialog
import doit.doittestapplication.ui.login.LoginActivity
import doit.doittestapplication.ui.main.add_picture.AddPictureActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*


class MainActivity : MainBarActivity(), MainView, PicturesListAdapter.OnItemClickListener {

    @InjectPresenter
    lateinit var presenter : MainPresenter

    private lateinit var adapter: PicturesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Storage.prefs.contains("token")){
            showLoginScreen()
        } else if (Storage.getToken().equals("")){
            showLoginScreen()
        }

        adapter = PicturesListAdapter(this)
        adapter.setListener(this)
        recyclerView.adapter = adapter
        val staggeredGridVertical = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridVertical.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = staggeredGridVertical

        swRefresh.setOnRefreshListener { presenter.getAllPictures(true) }
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllPictures(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId) {
            R.id.action_menu_logout -> {
                logout()
                return true
            }

            R.id.action_menu_generate_gif -> {
                presenter.getGifPicture()
                return true
            }

            R.id.action_menu_add_picture -> {
                startActivity<AddPictureActivity>()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPictureSelected(picture: PictureResponse.Image) {
        displayPictureDialog(picture.smallImagePath, picture.description, picture.hashtag)
    }

    override fun showPictures(pictures: PictureResponse) {
        adapter.setPictures(pictures.images)
    }

    override fun showGifPicture(gif: GifResponse) {
        displayPictureDialog(gif.gif, "", "")
    }

    override fun hideSwRefresh() {
        swRefresh.isRefreshing = false
    }

    override fun showLoginForm() {
        finish()
        startActivity<LoginActivity>()
    }

    private fun showLoginScreen(){
        finish()
        startActivity<LoginActivity>()
    }

    private fun displayPictureDialog(imagePath: String, description : String, hashTag : String) {

        val pictureDialogUi by lazy {
            contentView?.let {
                PictureDialog(AnkoContext.create(this, it))
            }
        }

        Glide.with(this)
                .load(imagePath)
                .into( pictureDialogUi?.ivPicture!!)

        pictureDialogUi?.tvDescription!!.text = description
        pictureDialogUi?.tvHashTag!!.text = hashTag

        pictureDialogUi?.okButton?.setOnClickListener {
            pictureDialogUi!!.dialog.dismiss()
        }
    }

    private fun logout() {
        alert(getString(R.string.logout_message), getString(R.string.logout_title)) {
            yesButton {
                presenter.logout()
            }
            noButton {}
        }.show()
    }
}
