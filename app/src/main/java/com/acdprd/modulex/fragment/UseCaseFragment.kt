package com.acdprd.modulex.fragment

import android.util.Log
import com.acdprd.baseproject.utils.extensions.tag
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.FragmentUseCaseBinding
import com.acdprd.modulex.fragment.basevb.BaseVbFragment
import com.acdprd.modulex.fragment.basevb.viewBindingDelegate
import com.acdprd.modulex.usecase.LoginUseCase
import com.acdprd.modulex.utils.observers.LambdaSingle
import com.acdprd.modulex.utils.observers.SimpleSingle
import io.reactivex.Single
import io.reactivex.SingleObserver
import java.util.concurrent.TimeUnit

class UseCaseFragment : BaseVbFragment<FragmentUseCaseBinding>() {
    private val useCase = LoginUseCase(true)
    private var obsId = 0

    override val binding: FragmentUseCaseBinding by viewBindingDelegate()
    override fun getLayoutRes(): Int = R.layout.fragment_use_case

    override fun onCreateView() {
        binding.tvButton.setOnClickListener { tryUseCase() }
    }

    private fun tryUseCase() {

        fun getObs(id: Int): SingleObserver<Int> {
            return LambdaSingle(
                onSuccess = {
                    Log.w(tag(), "obs${id} success $it")
                },
                onError = {
                    Log.w(tag(), "obs${id} error")
                },
                onSubscribe = {
                    Log.w(tag(), "obs${id} onsub ")
                }
            )
        }

        val id1 = ++obsId
        val obs1: SingleObserver<Int> = getObs(id1)
        val id2 = ++obsId
        val obs2: SingleObserver<Int> = getObs(id2)
        val id3 = ++obsId
        val obs3: SingleObserver<Int> = getObs(id3)
        useCase.subscribe2(obs1)
        Single.timer(500, TimeUnit.MILLISECONDS).subscribe(SimpleSingle {
            useCase.subscribe2(obs2)
            Single.timer(2500, TimeUnit.MILLISECONDS).subscribe(SimpleSingle {
                useCase.subscribe2(obs3)
            })
        })
    }

}