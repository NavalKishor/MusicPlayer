package com.concept.aop.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.concept.aop.databinding.RvSongRowItemBinding
import com.concept.aop.ui.model.Song
import com.concept.aop.utils.toast
import com.squareup.picasso.Picasso


class SongListAdapter (private val items: List<Song>, private var mListener: OnItemClickListener? = null) : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding  = RvSongRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SongViewHolder(binding,mListener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
     /*   mListener?.let {
            holder.setOnItemClickListener(it)
        }*/

       holder.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SongViewHolder(binding: RvSongRowItemBinding,var mListener: OnItemClickListener?=null) :RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        private var viewBinding: RvSongRowItemBinding=binding
        init {
          viewBinding.root.setOnClickListener(this)
        }
        override fun onClick(item: View?) {
            mListener?.onItemClick(adapterPosition)
        }

       /* fun setOnItemClickListener(listener: OnItemClickListener) {
            mListener = listener
        }*/

        fun bind(data: Song) {
            with(viewBinding){
                tvDesc.text=data.desc
                tvTitle.text=data.title
                try {
                    if(data.linkImg.contains("http"))
                        Picasso.get().load(data.linkImg).into(ivPic)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

 /*   fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }*/

}
