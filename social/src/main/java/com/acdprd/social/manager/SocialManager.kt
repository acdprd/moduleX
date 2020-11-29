package com.acdprd.social.manager

import android.content.Intent
import androidx.fragment.app.Fragment
import com.acdprd.social.model.UserSocialData

interface SocialManager {
    fun start(fragment: Fragment, userDataCallback: ((UserSocialData?) -> Unit)?)
    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun logout(resultCallback: ((Boolean) -> Unit)? = null)
}