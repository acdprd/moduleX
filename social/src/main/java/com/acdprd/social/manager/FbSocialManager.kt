package com.acdprd.social.manager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.acdprd.social.model.UserSocialData
import com.facebook.*
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import org.json.JSONException

/**
 * манагер для работы с фб
 */
open class FbSocialManager : SocialManager {
    private var accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
    private var callbackManager: CallbackManager = CallbackManager.Factory.create()
    private var resultListener: ((UserSocialData?) -> Unit)? = null
    private var fragment: Fragment? = null

    override fun start(fragment: Fragment, userDataCallback: ((UserSocialData?) -> Unit)?) {
        resultListener = userDataCallback
        this.fragment = fragment
        tryLogin()
        tryProfileRequest()
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun logout(resultCallback: ((Boolean) -> Unit)?) {
        Companion.logout(resultCallback)
    }

    private fun tryProfileRequest() {
        accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            getFacebookUserProfile()
        } else {
            LoginManager.getInstance()
                .logInWithReadPermissions(fragment, listOf(Const.PUBLIC_PROFILE, Const.EMAIL))
        }
    }

    private fun getFacebookUserProfile() {
        var id: String? = null
        var mail: String? = null
        var name: String? = null
        var firstName: String? = null
        var lastName: String? = null

        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            try {
                id = `object`.getString(Const.ID)
                name = `object`.getString(Const.NAME)
                mail = `object`.optString(Const.EMAIL)
                firstName = `object`.optString(Const.FIRST_NAME)
                lastName = `object`.optString(Const.LAST_NAME)
            } catch (e: JSONException) {
            }
            val body = UserSocialData().also { data ->
                //maybe interceptors for fields
                data[UserSocialData.EMAIL] = mail
                data[UserSocialData.FIRST_NAME] = firstName
                data[UserSocialData.LAST_NAME] = lastName
                data[UserSocialData.SOCIAL_ID] = id
            }
            resultListener?.invoke(body)
        }

        val parameters = Bundle()
        //maybe interceptors for fields
        parameters.putString(
            Const.FIELDS,
            arrayListOf(
                Const.ID,
                Const.NAME,
                Const.FIRST_NAME,
                Const.LAST_NAME,
                Const.EMAIL
            ).joinToString(separator = ", ")
        )
        request.parameters = parameters
        request.executeAsync()
    }

    private fun tryLogin() {
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                accessToken = result.accessToken
                tryProfileRequest()
//                    onResult(result)
            }

            override fun onCancel() {
                //todo
            }

            override fun onError(error: FacebookException) {
                //todo
                error.printStackTrace()
            }
        })
    }

    @JvmOverloads
    fun share(
        linkTo: String?,
        quote: String? = null,
        frg: Fragment,
        callback: FacebookCallback<Sharer.Result>
    ) {
        val shareDialog = ShareDialog(frg)
        shareDialog.show(getShareContent(linkTo, quote))
        shareDialog.registerCallback(callbackManager, callback)
    }

    private fun getShareContent(url: String?, quote: String? = null): ShareLinkContent {
        return ShareLinkContent.Builder()
            .setContentUrl(Uri.parse(url))
            .build()
    }

    companion object {
        fun logout(result: ((Boolean) -> Unit)? = null) {
            LoginManager.getInstance().logOut()
            result?.invoke(AccessToken.getCurrentAccessToken() == null)
        }
    }

    object Const {
        const val PUBLIC_PROFILE = "public_profile"
        const val EMAIL = "email"
        const val NAME = "name"
        const val FIELDS = "fields"
        const val ID = "id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val LINK = "link"
    }

}