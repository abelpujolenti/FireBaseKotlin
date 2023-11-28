package com.enti.dostres.cdi.abelpujol.modulodosfirabase.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enti.dostres.cdi.abelpujol.modulodosfirabase.R
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout

class LoginScreen : Fragment() {

    lateinit var fragmentView : View

    val userNameContainer by lazy {fragmentView.findViewById<MaterialCardView>(R.id.usernameInputContainer)}
    val userNameInput by lazy {fragmentView.findViewById<TextInputLayout>(R.id.usernameInput)}

    val passwordContainer by lazy {fragmentView.findViewById<MaterialCardView>(R.id.passwordInputContainer)}
    val passwordInput by lazy {fragmentView.findViewById<TextInputLayout>(R.id.passwordInput)}

    val verifyPasswordContainer by lazy {fragmentView.findViewById<MaterialCardView>(R.id.verifyPasswordInputContainer)}
    val verifyPasswordInput by lazy {fragmentView.findViewById<TextInputLayout>(R.id.verifyPasswordInput)}

    val emailLoginButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.loginButton)}
    val registerButton by lazy { fragmentView.findViewById<MaterialButton>(R.id.registerButton)}
    val googleAuthButton by lazy { fragmentView.findViewById<SignInButton>(R.id.login_google_button)}

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

        emailLoginButton.setOnClickListener { EmailLogin() }
        registerButton.setOnClickListener { StartRegister() }
        googleAuthButton.setOnClickListener { GoogleAuth() }

    }

    private fun EmailLogin(){

    }

    private fun StartRegister(){
        verifyPasswordContainer.visibility = View.VISIBLE

        emailLoginButton.text = getString(R.string.back_to_login_button)
        registerButton.text = getString(R.string.end_register_button)

        emailLoginButton.setOnClickListener {

            verifyPasswordContainer.visibility = View.GONE

            emailLoginButton.text = getString(R.string.login_button)
            registerButton.text = getString(R.string.register_button)


            emailLoginButton.setOnClickListener { EmailLogin() }
            registerButton.setOnClickListener { StartRegister() }
        }

        registerButton.setOnClickListener { EndRegister() }
    }

    private fun EndRegister(){

    }

    private fun GoogleAuth(){

    }
}