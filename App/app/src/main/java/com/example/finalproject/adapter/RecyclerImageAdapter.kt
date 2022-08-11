package com.example.finalproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ItemCaptureImagesBinding


class RecyclerImageAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerImageAdapter.ViewHolder>() {
    private val list: ArrayList<Uri> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemCaptureImagesBinding.inflate(inflater)
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addImage(uri: Uri) {
        list.add(uri)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val bind: ItemCaptureImagesBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(uri: Uri) {
            Glide.with(context).load(uri).into(bind.imageCapture)
        }
    }

}