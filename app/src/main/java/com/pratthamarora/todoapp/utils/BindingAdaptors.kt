package com.pratthamarora.todoapp.utils

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pratthamarora.todoapp.R

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAdd")
        @JvmStatic
        fun navigateToAdd(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDB: MutableLiveData<Boolean>) {
            when (emptyDB.value) {
                true -> view.isVisible = true
                false -> view.isGone = true
            }
        }

    }
}