package com.acdprd.adapterandviews.adapter.pager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

abstract class BasePagerAdapter<T : Any>(var items: List<T>) : PagerAdapter() {
    var itemViewListener: (Int, T) -> Unit = { _, _ -> }

    abstract fun makeView(context: Context, item: T): View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = getItem(position)
        val v = makeView(container.context, item)
        container.addView(v)
        v.setOnClickListener { itemViewListener.invoke(position, item) }
        return v
    }

    protected open fun getItem(position: Int): T {
        return items[position]
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun getCount(): Int = items.size

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }
}