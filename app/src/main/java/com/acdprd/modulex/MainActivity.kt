package com.acdprd.modulex

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.acdprd.filegsonrepository.BaseRepositoryInteractor
import com.acdprd.filegsonrepository.FileLocalStore
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        makeStore()
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