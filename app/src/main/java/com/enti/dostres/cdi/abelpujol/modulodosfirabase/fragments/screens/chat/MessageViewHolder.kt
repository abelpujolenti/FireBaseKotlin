package com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.screens.chat

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.models.DataBaseMessage
import com.google.android.material.textview.MaterialTextView

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val container by lazy {view.findViewById<LinearLayout>(R.id.message_container)}
    private val message by lazy {view.findViewById<MaterialTextView>(R.id.message_text)}
    private val image by lazy {view.findViewById<ImageView>(R.id.message_image)}

    fun SetupWithmessage(dataBaseMessage: DataBaseMessage){

        if (dataBaseMessage.userId == MyFirebase.authentication.GetUser()?.id){
            container.gravity = Gravity.RIGHT
        }else{
            container.gravity = Gravity.LEFT
        }

        dataBaseMessage.message?.let { text ->
            message.text = text
            message.visibility = View.VISIBLE
        } ?: kotlin.run {
            message.visibility = View.GONE
        }

        dataBaseMessage.imageUrl?.let {
            //TODO load image
            message.visibility = View.VISIBLE
        } ?: kotlin.run {
            message.visibility = View.GONE
        }
    }

}