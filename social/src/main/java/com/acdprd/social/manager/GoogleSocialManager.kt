package com.acdprd.social.manager

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.acdprd.social.model.UserSocialData
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.Task

/**
 * манагер для работы с гуглом
 */
class GoogleSocialManager(private var serverClientId: String) : SocialManager {
    private var userDataCallback: ((UserSocialData?) -> Unit)? = null
    private var fragment: Fragment? = null


    override fun start(fragment: Fragment, userDataCallback: ((UserSocialData?) -> Unit)?) {
        this.fragment = fragment
        this.userDataCallback = userDataCallback

        Auth.GoogleSignInApi
            .signOut(getClient(fragment.context)?.asGoogleApiClient())
            .setResultCallback {
                val client = getClient(fragment.context)
                val intent = client?.signInIntent
                fragment.startActivityForResult(intent, GOOGLE_AUTH_CODE)
            }
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_AUTH_CODE) {
            handleSignInResultAndMakeBody(
                GoogleSignIn.getSignedInAccountFromIntent(data),
                userDataCallback
            )
        }
    }

    private fun getSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .requestProfile()
            .requestId()
            .build()
    }

    private fun getClient(context: Context?): GoogleSignInClient? {
        return if (context != null) {
            GoogleSignIn.getClient(context, getSignInOptions())
        } else {
            null
        }
    }

    fun handleSignInResult(
        completedTask: Task<GoogleSignInAccount>,
        accountListener: (GoogleSignInAccount?) -> Unit
    ) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            accountListener.invoke(account)
        } catch (e: ApiException) {
            log("signInResult:failed code=" + e.statusCode + e.message)
            e.printStackTrace()
            accountListener.invoke(null)
        }
    }

    private fun handleSignInResultAndMakeBody(
        completedTask: Task<GoogleSignInAccount>,
        accountListener: ((UserSocialData?) -> Unit)?
    ) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            handleGoogleSignInAccount(account, accountListener)
        } catch (e: ApiException) {
            log("signInResult:failed code=" + e.statusCode + e.message)
            e.printStackTrace()
            accountListener?.invoke(null)
        }
    }

    private fun handleGoogleSignInAccount(
        lastAccount: GoogleSignInAccount?,
        accountListener: ((UserSocialData?) -> Unit)?
    ) {
        val body = UserSocialData().also { data ->
            //maybe interceptors for fields
            data[UserSocialData.SOCIAL_ID] = lastAccount?.id
            data[UserSocialData.FIRST_NAME] = lastAccount?.givenName
            data[UserSocialData.LAST_NAME] = lastAccount?.familyName
            data[UserSocialData.EMAIL] = lastAccount?.email
        }
        accountListener?.invoke(body)
    }

    //todo check
    fun logout(context: Context, resultCallback: (Status) -> Unit) {
        Auth.GoogleSignInApi.signOut(getClient(context)?.asGoogleApiClient())
            .setResultCallback { resultCallback.invoke(it) }
    }

    fun checkLastSigned(context: Context, accountListener: (GoogleSignInAccount?) -> Unit) {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        accountListener.invoke(account)
    }

    private fun makeLoginBody(completedTask: Task<GoogleSignInAccount>): GoogleSignInAccount? {
        return completedTask.getResult(ApiException::class.java)
    }

    override fun logout(resultCallback: ((Boolean) -> Unit)?) {
        Companion.logout(getClient(fragment?.context), resultCallback)
    }

    companion object {
        private var INNER_LOG = false
        const val GOOGLE_AUTH_CODE = 7878
        private val TAG = GoogleSocialManager::class.java.simpleName

        @JvmStatic
        fun logout(client: GoogleSignInClient?, resultCallback: ((Boolean) -> Unit)? = null) {
            Auth.GoogleSignInApi
                .signOut((client)?.asGoogleApiClient())
                .setResultCallback {
                    resultCallback?.invoke(it.isSuccess)
                }
        }

        @JvmStatic
        private fun log(what: Any, where: Any? = null) {
            if (INNER_LOG) {
                Log.w(TAG, "what: $what | where: $where")
            }
        }
    }
}