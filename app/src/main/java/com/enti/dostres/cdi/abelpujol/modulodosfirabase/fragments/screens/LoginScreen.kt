package com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.screens

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.MyFirebase
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.models.DataBaseUser
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.components.AppDrawer

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ActionCodeSettings
import java.util.Date

class LoginScreen: Fragment() {

    lateinit var fragmentView: View

    /*val usernameContainer by lazy { fragmentView.findViewById<MaterialCardView>(R.id.usernameInputContainer) }
    val usernameInput by lazy { fragmentView.findViewById<TextInputLayout>(R.id.usernameInput) }

    val passwordContainer by lazy { fragmentView.findViewById<MaterialCardView>(R.id.passwordInputContainer) }
    val passwordInput by lazy { fragmentView.findViewById<TextInputLayout>(R.id.passwordInput) }

    val verifyPasswordContainer by lazy { fragmentView.findViewById<MaterialCardView>(R.id.verifyPasswordInputContainer) }
    val verifyPasswordInput by lazy { fragmentView.findViewById<TextInputLayout>(R.id.verifyPasswordInput) }

    val emailLoginButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.loginButton) }
    val registerButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.registerButton) }*/
    val googleAuthButton by lazy { fragmentView.findViewById<SignInButton>(R.id.login_google_button) }

    val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.login_screen, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*emailLoginButton.setOnClickListener { emailLogin() }
        registerButton.setOnClickListener { startRegister() }*/
        googleAuthButton.setOnClickListener { googleAuth() }
    }

    private fun emailLogin() {

    }

    private fun startRegister() {
        /*verifyPasswordContainer.visibility = View.VISIBLE

        emailLoginButton.text = getString(R.string.back_to_login_button)
        registerButton.text = getString(R.string.end_register_button)

        emailLoginButton.setOnClickListener {

            verifyPasswordContainer.visibility = View.GONE

            emailLoginButton.text = getString(R.string.login_button)
            registerButton.text = getString(R.string.register_button)

            emailLoginButton.setOnClickListener { emailLogin() }
            registerButton.setOnClickListener { startRegister() }
        }

        registerButton.setOnClickListener { endRegister() }*/
    }

    private fun endRegister() {

    }

    private fun googleAuth() {
        val providers = arrayListOf(
            //AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signIntIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signIntIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

        if(result.resultCode != RESULT_OK) {
            MyFirebase.crashlytics.logSimpleError("Login Error") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        val authUser = MyFirebase.authentication.GetAuthenticationDatabaseUser()?: kotlin.run {
            MyFirebase.crashlytics.logSimpleError("Login Error No User") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        val id = authUser.id ?: kotlin.run {
            MyFirebase.crashlytics.logSimpleError("Login Error No ID") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        MyFirebase.dataBase.find<DataBaseUser>(id, authUser.GetTable(),
            onSuccess = { dbUser ->
                dbUser.lastLogin = Date()

                finalSaveUser(dbUser)
            },
            onFailure = {
                finalSaveUser(authUser)
            })
    }

    private fun finalSaveUser(dbUser: DataBaseUser) {
        MyFirebase.dataBase.save(dbUser,
            onSuccess = { dbUser ->
                MyFirebase.authentication.SetCurrentUser(dbUser)
                sendToastSuccessAndClose()
            },
            onFailure = {
                sendToastError()
            }
        )
    }

    private fun sendToastError() {
        Snackbar.make(
            AppDrawer.get().fragmentView,
            getString(R.string.login_error),
            Snackbar.LENGTH_LONG)
            .show()
    }

    private fun sendToastSuccessAndClose() {
        Snackbar.make(
            AppDrawer.get().fragmentView,
            getString(R.string.user_login_message, MyFirebase.authentication.GetUser()?.username),
            Snackbar.LENGTH_LONG)
            .show()
        parentFragmentManager.popBackStack()
    }
}