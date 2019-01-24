package doit.doittestapplication.ui.main

import doit.doittestapplication.data.api.model.GifResponse
import doit.doittestapplication.data.api.model.PictureResponse
import doit.doittestapplication.ui.base.BaseView

interface MainView : BaseView {

    fun showPictures(pictures : PictureResponse)

    fun showGifPicture(gif : GifResponse)

    fun hideSwRefresh()
}