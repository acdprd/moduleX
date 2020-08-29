package com.acdprd.modulex.fragment

import com.acdprd.adapterandviews.adapter.rv.decoration.IfAnyOfTypesEqualTypesDecoration
import com.acdprd.modulex.Const
import com.acdprd.modulex.R
import com.acdprd.modulex.adapter.ListItem
import com.acdprd.modulex.adapter.SomeAdapter
import com.acdprd.modulex.databinding.FragmentListBinding
import com.acdprd.modulex.model.SomeModel1
import com.acdprd.modulex.model.SomeModel2
import kotlin.random.Random

class ListFragment : BaseFragment<FragmentListBinding>() {
    private var adapter = SomeAdapter()

    override fun getLayoutRes(): Int = R.layout.fragment_list

    override fun onCreateView() {
        initList()
        getData()
    }

    private fun getData(count: Int = 50) {
        val list = mutableListOf<ListItem>()


        for (i in 1..count) {
            if (i % 2 == 0) {
                list.add(SomeModel1("some text $i"))
            } else {
                list.add(SomeModel2(Random.nextInt()))
            }
        }


        setData(list)
    }

    private fun setData(list: MutableList<ListItem>) {
        adapter.setItems(list)
    }

    private fun initList() {
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(
            IfAnyOfTypesEqualTypesDecoration(
                adapter,
                listOf(Const.ViewType.SOME_TYPE_1, Const.ViewType.SOME_TYPE_2),
                resources.getDrawable(
                    DEFAULT_BACKGROUND
                )
            )
        )
        //fixme only first 3-4 elements
    }


    companion object {
        const val DEFAULT_BACKGROUND = R.drawable.divider_gray_e8_h1
    }
}