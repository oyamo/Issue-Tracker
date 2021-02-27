/*
 * Copyright (c) 2021 Oyamo Brian.
 * Last modified on 2/21/21 9:08 PM
 *
 *
 */

package com.oyasis.issuetracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.OAuthProvider
import com.oyasis.issuetracker.IssueTrackerActivity
import com.oyasis.issuetracker.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class LoginActivity : IssueTrackerActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginManager: LoginManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var oAuthProvider: OAuthProvider.Builder
    private val RC_SIGN_IN = 12

    // clientid 602851500498-dbtotstd7i28t8ibrvr2ra42q85tn4av.apps.googleusercontent.com
    // clientsecret 8uPCm1dODvGVnCTjRHdA9paI
    // https://firebase.google.com/docs/auth/android/github-auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        //val account = GoogleSignIn.getLastSignedInAccount(this)
        setContentView(binding.root)
    }


    override fun onResume() {
        super.onResume()
        // Initialise All the Login Providers
        initFBOAUTH()
        initGOAUTH()
        initGHOAUTH()
    }

    fun initGHOAUTH() {
        GlobalScope.launch(Dispatchers.IO) {
            val pendingResultTask = firebaseAuth.pendingAuthResult

            if (pendingResultTask != null) {
                // There's something already here! Finish the sign-in for your user.
                pendingResultTask
                    .addOnSuccessListener(
                        OnSuccessListener {
                            // User is signed in.
                            // IdP data available in
                            // authResult.getAdditionalUserInfo().getProfile().
                            // The OAuth access token can also be retrieved:
                            // authResult.getCredential().getAccessToken().
                        })
                    .addOnFailureListener {
                        // Handle failure.
                    }
            } else {
                // There's no pending result so you need to start the sign-in flow.
                // See below.
            }

            oAuthProvider = OAuthProvider.newBuilder("github.com");
            oAuthProvider.scopes = listOf("user:email", "read:user", "read:org")

        }
    }

    private fun initGOAUTH() {
        GlobalScope.launch(Dispatchers.IO) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso);
        }
    }

    private fun initFBOAUTH() {
        GlobalScope.launch(Dispatchers.IO) {

            FacebookSdk.sdkInitialize(this@LoginActivity)
            callbackManager = CallbackManager.Factory.create()
            loginManager = LoginManager.getInstance()
            loginManager.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        Timber.d("Login Success")
                    }

                    override fun onCancel() {
                        Toast.makeText(this@LoginActivity, "Login Cancel", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(exception: FacebookException) {
                        Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }
    }
    // Launches the sign up/in with Google Account Selector
    fun signInWithGoogle(view: View) {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    // Launches the sign in/up with Github page
    fun signInWithGithub(view: View) {
        firebaseAuth
            .startActivityForSignInWithProvider( /* activity= */this, oAuthProvider.build())
            .addOnSuccessListener {
                // User is signed in.
                // IdP data available in
                // authResult.getAdditionalUserInfo().getProfile().
                // The OAuth access token can also be retrieved:
                // authResult.getCredential().getAccessToken().

            }
            .addOnFailureListener {
                // Handle failure.
            }

    }

    // Launches the sign up/in with Facebook page
    fun signInWithFacebook(view: View) {
        loginManager.logInWithReadPermissions(this, listOf("public_profile"));

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return
        }

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.e(e)
        }
    }
}