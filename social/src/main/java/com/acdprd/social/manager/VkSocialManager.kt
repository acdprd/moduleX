package com.acdprd.social.manager

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.acdprd.social.model.UserSocialData
import com.acdprd.social.network.vk.VkRequestListener
import com.acdprd.social.network.vk.model.VkInfo
import com.acdprd.social.network.vk.model.VkResponse
import com.google.gson.Gson
import com.vk.sdk.*
import com.vk.sdk.api.*
import com.vk.sdk.dialogs.VKShareDialogBuilder
import java.util.*

/**
 * манагер для работы с вк
 */

open class VkSocialManager : SocialManager {
    private var vkToken: VKAccessToken? = VKAccessToken.currentToken()
    private var vkCallback: VKCallback<VKAccessToken>
    private var userDataCallback: ((UserSocialData?) -> Unit)? = null

    init {
        vkCallback = object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {
                vkToken = res
                tryProfileRequestVkApi()
            }

            override fun onError(error: VKError?) {
                println(error?.errorMessage)
            }
        }
    }

    override fun start(fragment: Fragment, userDataCallback: ((UserSocialData?) -> Unit)?) {
        this.userDataCallback = userDataCallback

        if (isAuth()) {
            tryProfileRequestVkApi()
        } else {
            fragment.startActivityForResult(
                getIntentToLogin(fragment.context),
                VKServiceActivity.VKServiceType.Authorization.outerCode
            )
        }
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        VKSdk.onActivityResult(requestCode, resultCode, data, vkCallback)
    }

    override fun logout(resultCallback: ((Boolean) -> Unit)?) {
        Companion.logout(resultCallback)
    }

    private fun tryProfileRequestVkApi() {
        val request: VKRequest =
            VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, vkToken?.userId))
        request.executeWithListener(VkRequestListener({ response ->
            //{"nameValuePairs":{"response":{"values":[{"nameValuePairs":{"id":1111,"first_name":"aaa","last_name":"aaa"}}]}}} //todo wip
            userDataCallback?.invoke(
                makeLoginBodyFromJson(
                    response.responseString,
                    vkToken?.userId
                )
            )
        }))
    }

    protected open fun makeLoginBodyFromJson(json: String, userId: String?): UserSocialData {
        val answer: VkResponse = Gson().fromJson(json, VkResponse::class.java)
        var firstAnswer: VkInfo? = null
        if (answer.values.isNullOrEmpty()) {
            firstAnswer = answer.values?.getOrNull(0)
        }
        val res = UserSocialData().also { data ->
            //maybe interceptors for fields
            data[UserSocialData.EMAIL] = firstAnswer?.email
            data[UserSocialData.FIRST_NAME] = firstAnswer?.firstName
            data[UserSocialData.LAST_NAME] = firstAnswer?.lastName
            data[UserSocialData.SOCIAL_ID] = userId
        }
        return res
    }

    private fun isAuth(): Boolean = VKAccessToken.currentToken() != null

    fun tryShare(
        fragment: Fragment,
        shareText: String?,
        linkTitle: String?,
        shareLink: String?,
        shareDialog: (VKShareDialogBuilder) -> Unit
    ) {
        setVkCallback { tryShare(fragment, shareText, linkTitle, shareLink, shareDialog) }
        if (isAuth()) {
            shareDialog.invoke(shareWithLink(shareText, linkTitle, shareLink))
        } else {
            fragment.startActivityForResult(
                getIntentToLogin(fragment.context),
                VKServiceActivity.VKServiceType.Authorization.outerCode
            )
        }
    }

    private fun setVkCallback(onResult: () -> Unit) {
        vkCallback = object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {
                vkToken = res
                onResult.invoke()
            }

            override fun onError(error: VKError?) {
                println(error?.errorMessage)
            }
        }
    }

    private fun shareWithLink(
        shareText: String?,
        linkTitle: String?,
        shareLink: String?
    ): VKShareDialogBuilder {
        val builder = VKShareDialogBuilder()
        builder.setText(shareText)
        builder.setAttachmentLink(linkTitle, shareLink)

        return builder
    }

    companion object {
        fun getIntentToLogin(context: Context?): Intent {
            if (context != null) {
                val intent =
                    Intent(
                        context,
                        VKServiceActivity::class.java
                    )//todo https://github.com/VKCOM/vk-android-sdk/issues/170
                intent.putExtra("arg1", "Authorization")
                val scopes = ArrayList<String>()
                //maybe interceptors for fields
                scopes.add(VKScope.OFFLINE)
                scopes.add(VKScope.EMAIL)
                scopes.add(VKScope.WALL)

                intent.putStringArrayListExtra("arg2", scopes)
                intent.putExtra("arg4", VKSdk.isCustomInitialize())
                return intent
            } else {
                return Intent()//empty intent
            }
        }

        fun logout(resultCallback: ((Boolean) -> Unit)? = null) {
            VKSdk.logout()
            resultCallback?.invoke(VKAccessToken.currentToken() == null)
        }
    }
}