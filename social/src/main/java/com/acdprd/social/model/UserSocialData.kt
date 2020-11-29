package com.acdprd.social.model

class UserSocialData(
    map: MutableMap<String, String?> = mutableMapOf()
) :
    MutableMap<String, String?> by map {


    companion object {
        const val SOCIAL_ID = "social_id"
        const val EMAIL = "email"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
    }
}