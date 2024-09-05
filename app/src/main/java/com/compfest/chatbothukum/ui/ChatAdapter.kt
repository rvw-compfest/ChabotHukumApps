package com.compfest.chatbothukum.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.compfest.chatbothukum.R
import com.compfest.chatbothukum.core.domain.model.Message

class ChatAdapter(private val messageArrayList: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val SELF = 100

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = if (viewType == SELF) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_item_self, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_item_other, parent, false)
        }

        return ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageArrayList[position]
        return if (message.isUser) SELF else position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageArrayList[position]
        (holder as ViewHolder).message.text = message.message
    }

    override fun getItemCount(): Int {
        return messageArrayList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var message: TextView = view.findViewById(R.id.message)
    }
}
