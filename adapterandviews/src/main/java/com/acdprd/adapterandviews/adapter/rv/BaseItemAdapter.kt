@file:Suppress("MemberVisibilityCanBePrivate")

package com.acdprd.adapterandviews.adapter.rv

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acdprd.adapterandviews.adapter.rv.holder.BaseItemHolder
import java.util.*

abstract class BaseItemAdapter<M : Any, V : View, VH : BaseItemHolder<V, M>> :
    RecyclerView.Adapter<VH>() {

    @Suppress("PropertyName")
    protected var _items = mutableListOf<M>()
    var itemsClickListener: ((M) -> Unit)? = null

    override fun getItemCount(): Int = _items.size

    abstract fun getCustomView(context: Context, viewType: Int): V?

    abstract fun getViewHolder(view: V): VH

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VH {
        val view = getCustomView(parent.context, viewType)

        return when (view == null) {
            true -> throw IllegalArgumentException("view is null (viewType:$viewType)")
            false -> getViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        val item = _items[position]
        holder.bindItem(item)
        holder.baseItemView.setOnClickListener { itemsClickListener?.invoke(item) }
    }

    open fun setItems(items: Iterable<M>) {
        this._items = items.toMutableList()
        notifyDataSetChanged()
    }

    open fun addItems(itemList: List<M>) {
        _items.addAll(itemList)
        notifyDataSetChanged()
    }

    open fun addItems(position: Int, itemList: List<M>) {
        _items.addAll(position, itemList)
        notifyDataSetChanged()
    }

    open fun addItem(item: M) {
        _items.add(item)
        notifyItemInserted(_items.size - 1)
    }

    open fun addItem(position: Int, item: M) {
        _items.add(position, item)
        notifyItemInserted(position)
    }

    open fun removeAt(position: Int) {
        _items.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun findItemPosition(item: M?): Int = _items.indexOf(item)

    open fun remove(item: M) {
        val pos = findItemPosition(item)
        _items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    open fun removeAll() {
        _items.clear()
        notifyDataSetChanged()
    }

    open fun swapElements(pos1: Int, pos2: Int) {
        Collections.swap(_items, pos1, pos2)
        notifyItemMoved(pos1, pos2)
    }

    open fun getItem(position: Int): M {
        return _items[position]
    }
}