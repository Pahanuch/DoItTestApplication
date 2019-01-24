package doit.doittestapplication.ui.adapters

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import doit.doittestapplication.R
import doit.doittestapplication.data.api.model.PictureResponse


internal class PicturesListAdapter(private val mContext: Context) :RecyclerView.Adapter<PicturesListAdapter.PictureViewHolder>() {

    private var mPictures: List<PictureResponse.Image>? = arrayListOf()

    private var mListener: OnItemClickListener? = null

    fun setListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    fun setPictures(pictures: List<PictureResponse.Image>) {
        mPictures = pictures
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesListAdapter.PictureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)
        return PictureViewHolder(view)
    }

    override fun onBindViewHolder(holder: PicturesListAdapter.PictureViewHolder, position: Int) {

        val picture  = mPictures?.get(position)

        Glide.with(mContext)
                .load(picture!!.smallImagePath)
                .into(holder.ivPicture!!)

        holder.tvDescription!!.text = picture.description
        holder.tvHashTag!!.text = picture.hashtag

        holder.itemView.setOnClickListener { if (mListener != null) mListener!!.onPictureSelected(picture) }
        holder.itemView.tag = picture
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemCount(): Int {
        return mPictures!!.size
    }

    private fun getItem(position: Int): PictureResponse.Image {
        return mPictures!![position]
    }

    internal interface OnItemClickListener {
        fun onPictureSelected(picture: PictureResponse.Image)
    }

    internal class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPicture : AppCompatImageView? = itemView.findViewById(R.id.ivPicture)
        val tvDescription : AppCompatTextView? = itemView.findViewById(R.id.tvDescription)
        val tvHashTag : AppCompatTextView? = itemView.findViewById(R.id.tvHashTag)
    }

}