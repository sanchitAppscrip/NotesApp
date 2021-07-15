package com.extensions

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

fun RecyclerView.canScrollBottom(): Boolean = canScrollVertically(1)

fun RecyclerView.cannotScrollBottom(): Boolean = !canScrollBottom()