package com.acdprd.social.network.vk.model

import com.google.gson.annotations.SerializedName

class VkResponse(@SerializedName("response") var values: List<VkInfo>?)

class VkInfo(
    var id: Int?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    var lastName: String?,
    var email: String?
)