package com.gaugustini.listio.util

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.gaugustini.listio.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.isVisible = isGone
}

@BindingAdapter("title")
fun MaterialToolbar.setTitle(editing: Boolean) {
    title = if (editing) {
        resources.getString(R.string.edit_task)
    } else {
        resources.getString(R.string.new_task)
    }
}

@BindingAdapter("task")
fun TextInputEditText.setTask(str: String?) {
    if (str != null) {
        setText(str)
    }
}
