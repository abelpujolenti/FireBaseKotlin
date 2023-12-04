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

class LoginScreen : Fragment() {

    lateinit var fragmentView : View

    /*val userNameContainer by lazy {fragmentView.findViewById<MaterialCardView>(R.id.usernameInputContainer)}
    val userNameInput by lazy {fragmentView.findViewById<TextInputLayout>(R.id.usernameInput)}

    val passwordContainer by lazy {fragmentView.findViewById<MaterialCardView>(R.id.passwordInputContainer)}
    val passwordInput by lazy {fragmentView.findViewById<TextInputLayout>(R.id.passwordInput)}

    val verifyPasswordContainer by lazy {fragmentView.findViewById<MaterialCardView>(R.id.verifyPasswordInputContainer)}
    val verifyPasswordInput by lazy {fragmentView.findViewById<TextInputLayout>(R.id.verifyPasswordInput)}

    val emailLoginButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.loginButton)}
    val registerButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.registerButton)}*/
    val googleAuthButton by lazy { fragmentView.findViewById<SignInButton>(R.id.login_google_button)}

    val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { result ->
        this.OnSignInResult(result);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.login_screen, container, false);
        return fragmentView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*emailLoginButton.setOnClickListener { EmailLogin() }
        registerButton.setOnClickListener { StartRegister() }*/
        googleAuthButton.setOnClickListener { GoogleAuth() }

    }

    private fun EmailLogin(){

    }

    private fun StartRegister(){
        /*verifyPasswordContainer.visibility = View.VISIBLE

        emailLoginButton.text = getString(R.string.back_to_login_button)
        registerButton.text = getString(R.string.end_register_button)

        emailLoginButton.setOnClickListener {

            verifyPasswordContainer.visibility = View.GONE

            emailLoginButton.text = getString(R.string.login_button)
            registerButton.text = getString(R.string.register_button)


            emailLoginButton.setOnClickListener { EmailLogin() }
            registerButton.setOnClickListener { StartRegister() }
        }

        registerButton.setOnClickListener { EndRegister() }*/
    }

    private fun EndRegister()
    {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setEmailLink("abel.pujol@enti.cat")
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun GoogleAuth()
    {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName(
                "com.enti.dostres.cdi.abelpujol.modulodosfirabase",
                false,
                null
            )
            .setHandleCodeInApp(true)
            .setUrl("https://google.com")
            .build()

        val providers = arrayListOf(
            EmailBuilder()
                .enableEmailLinkSignIn()
                .setActionCodeSettings(actionCodeSettings)
                .build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setEmailLink("abel.pujol@enti.cat")
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun OnSignInResult(result: FirebaseAuthUIAuthenticationResult)
    {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK)
        {
            val userName =
            Snackbar.make(AppDrawer.get().fragmentView, getString(R.string.user_login_message,
                MyFirebase.authentication.GetUserName()), Snackbar.LENGTH_LONG)
                .show()
            parentFragmentManager.popBackStack()
        }
        else
        {
            MyFirebase.crashlytics.logSimpleError("Login Error"){
                key("code", result.resultCode)
                key("data", result.toString())
            }
            Snackbar.make(AppDrawer.get().fragmentView, getString(R.string.login_error), Snackbar.LENGTH_LONG)
                .show()
        }
    }
}