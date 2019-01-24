package doit.doittestapplication.ui.main.add_picture

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import doit.doittestapplication.R
import doit.doittestapplication.data.api.model.MapCoordinates
import doit.doittestapplication.ui.base.WithBakButtonBarActivity
import doit.doittestapplication.utils.AppUtil
import doit.doittestapplication.utils.PathUtil
import kotlinx.android.synthetic.main.activity_add_picture.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import doit.doittestapplication.utils.PermissionManager


class AddPictureActivity : WithBakButtonBarActivity(), AddPictureView {

    @InjectPresenter
    lateinit var presenter: AddPicturePresenter

    private var imageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_picture)

        ivPicture.setOnClickListener {

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
                            .into(ivPicture)

                } else {
                    return
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_picture_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            R.id.action_send_picture -> {

                var cancel = false

                val descriptionStr = etDescription.text.toString()
                val hashTagStr = etHashTag.text.toString()

                if (!isPictureLoaded()) {
                    cancel = true
                    showError(getString(R.string.picture_empty_error))
                } else if (!isDescriptionValid(descriptionStr)) {
                    cancel = true
                    showError(getString(R.string.error_field_required))
                } else if (!isHashTagValid(hashTagStr)) {
                    cancel = true
                    showError(getString(R.string.error_field_required))
                }

                if (!cancel) {
                    val file = File(PathUtil.getPath(this, imageFileUri))
                    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    val imageBody = MultipartBody.Part.createFormData("image", file.name, requestFile)
                    val coordinates = MapCoordinates(0F, 0F)

                    presenter.addPicture(imageBody, AppUtil.parseStringIntoRequestBody(descriptionStr), AppUtil.parseStringIntoRequestBody(hashTagStr),
                            AppUtil.parseStringIntoRequestBody(coordinates.latitude.toString()), AppUtil.parseStringIntoRequestBody(coordinates.longitude.toString()))
                }

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun successfullyLoaded() {
        Glide.with(this)
                .load(R.drawable.ic_avatar_holder)
                .into(ivPicture)
        etDescription.text!!.clear()
        etHashTag.text!!.clear()
        showMessage(getString(R.string.message_photo_uploaded_success))
    }

    private fun isPictureLoaded(): Boolean {
        return imageFileUri != null
    }

    private fun isDescriptionValid(description : String): Boolean {
        return !TextUtils.isEmpty(description)
    }

    private fun isHashTagValid(hashTag : String): Boolean {
        return !TextUtils.isEmpty(hashTag)
    }

    companion object {
        private const val RESULT_LOAD_IMAGE = 1
    }

}