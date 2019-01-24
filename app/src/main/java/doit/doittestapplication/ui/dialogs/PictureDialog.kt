package doit.doittestapplication.ui.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import doit.doittestapplication.R
import org.jetbrains.anko.*

class PictureDialog (ui: AnkoContext<View>) {

    var dialog: DialogInterface
    lateinit var ivPicture: ImageView
    lateinit var tvDescription: TextView
    lateinit var tvHashTag: TextView
    lateinit var okButton: TextView

    init {
        with(ui) {
            dialog = alert {
                customView {
                    verticalLayout {
                        padding = dip(16)

                        ivPicture = imageView(R.drawable.ic_avatar_holder).
                                lparams(width = 400, height = 400) {
                                    padding = dip(20)
                                    margin = dip(15)
                                    gravity = Gravity.CENTER
                                }

                        tvDescription = textView {
                            textSize = 20f
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent)

                        tvHashTag = textView {
                            textSize = 16f
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent)

                        okButton = textView(android.R.string.ok) {
                            textSize = 18f
                            gravity = Gravity.END
                        }
                    }
                }
            }.show()
        }
    }
}