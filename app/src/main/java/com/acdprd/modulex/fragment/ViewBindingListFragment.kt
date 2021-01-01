package com.acdprd.modulex.fragment

import android.content.Context
import android.util.Log
import androidx.viewbinding.ViewBinding
import com.acdprd.adapterandviews.adapter.rv.decoration.IfAnyOfTypesEqualTypesDecoration
import com.acdprd.baseproject.utils.extensions.tag
import com.acdprd.modulex.Const
import com.acdprd.modulex.R
import com.acdprd.modulex.adapter.ItemBAdapter
import com.acdprd.modulex.adapter.ListItem
import com.acdprd.modulex.databinding.FragmentViewBindingListBinding
import com.acdprd.modulex.fragment.basevb.BaseVbFragment
import com.acdprd.modulex.fragment.basevb.viewBindingDelegate
import com.acdprd.modulex.model.SomeModel3
import com.acdprd.modulex.model.SomeModel4
import com.acdprd.modulex.view.SomeView3
import com.acdprd.modulex.view.SomeView4
import com.acdprd.modulex.view.basevb.CustomListItemBView
import kotlin.random.Random

class ViewBindingListFragment : BaseVbFragment<FragmentViewBindingListBinding>() {
    private val adapter = Adapter()

    override val binding: FragmentViewBindingListBinding by viewBindingDelegate()
    override fun getLayoutRes(): Int = R.layout.fragment_view_binding_list

    override fun onCreateView() {
        log(1)
        initList()
        log(4)
        getData()
        log(9)
    }

    private fun initList() {
        log(2)
        binding.recyclerView.adapter = adapter

        log(3)
    }

    private fun getData(count: Int = 50) {
        log(5)
        val list = mutableListOf<ListItem>()


        for (i in 1..count) {
            if (i % 2 == 0) {
                list.add(SomeModel3("some text $i"))
            } else {
                list.add(SomeModel4(Random.nextInt()))
            }
        }


        setData(list)
        log(8)
    }

    private fun setData(list: MutableList<ListItem>) {
        log(7)
        adapter.setItems(list)
    }

    private class Adapter : ItemBAdapter() {
        override fun getCustomView(
            context: Context,
            viewType: Const.ViewType?
        ): CustomListItemBView<out ViewBinding, out ListItem>? {
            return when (viewType) {
                Const.ViewType.SOME_TYPE_3 -> SomeView3(context)
                Const.ViewType.SOME_TYPE_4 -> SomeView4(context)
                else -> null
            }
        }
    }

    companion object {
       private const val INNER_LOG = false

        @JvmStatic
        fun log(what: Any) {
            if (INNER_LOG) {
                Log.w(tag(), what.toString())
            }
        }
    }
}