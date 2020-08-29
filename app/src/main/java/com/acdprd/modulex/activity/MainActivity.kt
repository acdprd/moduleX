package com.acdprd.modulex.activity

import android.util.Log
import com.acdprd.baseproject.activity.CustomBindingActivity
import com.acdprd.filegsonrepository.FileLocalStore
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.ActivityMainBinding
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

class MainActivity : CustomBindingActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_main
    override fun getHostFragmentId(): Int = R.id.hostFragment

    override fun create() {

    }

    private fun makeStore() {
        val store = FileLocalStore(filesDir)

        fun a(){
            store.getData<List<Boolean>>("asd",object : TypeToken<List<Boolean>>(){}.type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    Log.w("TAG"," ${it.joinToString()}")
                })
        }

        store.saveData("asd", mutableListOf(true, true,false))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Action {
                    a()
                })
    }
}