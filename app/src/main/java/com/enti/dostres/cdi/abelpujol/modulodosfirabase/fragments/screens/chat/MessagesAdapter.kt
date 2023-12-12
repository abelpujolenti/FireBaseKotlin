package com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.screens.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.models.DataBaseMessage

class MessagesAdapter(table: RecyclerView): RecyclerView.Adapter<MessageViewHolder>() {

    var messages = mutableListOf<DataBaseMessage>()

    init {
        MyFirebase.dataBase.OnTableChange<DataBaseMessage>(DataBaseMessage().GetTable(), onChange =  { updatedMessages ->
            updatedMessages.sortBy { message ->
                message.timeStamp
            }

            val count = messages.count()
            val newCount = updatedMessages.count()
            val difference = newCount - count

            messages = updatedMessages

            if (difference > 0){
                notifyItemRangeInserted(count - 1, difference)
            }
            else
            {
                notifyDataSetChanged()
            }
            table.scrollToPosition(newCount - 1)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = MessageViewHolder(layoutInflater.inflate(R.layout.screen_chat_message_cell, parent, false))

        viewHolder.itemView.setOnClickListener {

        }

        return viewHolder
    }

    override fun getItemCount() = messages.count()

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.SetupWithmessage(messages[position])
    }

    fun AddMessage(text: String?, imageUrl: String?){
        val dataBaseMessage = DataBaseMessage(null, MyFirebase.authentication.GetUser()?.id, text, imageUrl)

        MyFirebase.dataBase.save(dataBaseMessage,
            onSuccess = {
            //TODO EXTRA CONTROL

        },  onFailure = {
            //TODO ERROR
        })
    }
}