package com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.screens.chat

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.MyApp
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.components.AppDrawer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class ChatScreen : Fragment() {

    lateinit var fragmentView: View
    val messageTable by lazy {fragmentView.findViewById<RecyclerView>(R.id.chat_messages_recycler)}
    val messageInput by lazy {fragmentView.findViewById<TextInputLayout>(R.id.chat_messages_input)}
    val messageImage by lazy {fragmentView.findViewById<ImageView>(R.id.chat_messages_image_container)}

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){ galleryUri ->
        OnImagePicked(galleryUri)
    }

    var currentImageUri: Uri? = null

    val messageAdapter by lazy { MessagesAdapter(messageTable) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageTable.layoutManager = LinearLayoutManager(MyApp.get())
        messageTable.adapter = messageAdapter

        messageInput.setStartIconOnClickListener {
            OpenImagePicker()
        }

        messageInput.setEndIconOnClickListener {
            SendMessage()
        }
    }

    fun SendMessage(){

        if (!MyFirebase.authentication.IsLoginActive()){

            MyApp.get().CloseKeyboard(messageInput)
            AppDrawer.get().OpenLogin()
            return
        }

        val inputText = messageInput.editText?.text.toString()
        val text = if(inputText != "") inputText else null

        val imageUri = currentImageUri ?: run {
            messageAdapter.AddMessage(text, null)
            messageInput.editText?.text?.clear()
            return
        }

        MyFirebase.storage.saveImage(imageUri,
            onSuccess = { newImageUri ->
                messageAdapter.AddMessage(text, newImageUri.toString())
                messageInput.editText?.text?.clear()

                OnImagePicked(null)

            },
            onFailure = {
                Snackbar.make(AppDrawer.get().fragmentView,
                    getString(R.string.chat_upload_image_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        )
    }

    fun OpenImagePicker(){
        galleryLauncher.launch("image/*")
    }

    fun OnImagePicked(galleryUri: Uri?){
        currentImageUri = galleryUri
        messageImage.setImageURI(currentImageUri)
        currentImageUri?.let {
            messageImage.visibility = View.VISIBLE
        } ?: run{
            messageImage.visibility = View.GONE
        }
    }
}