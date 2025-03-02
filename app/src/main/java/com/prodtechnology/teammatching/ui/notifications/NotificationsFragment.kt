package com.prodtechnology.teammatching.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prodtechnology.teammatching.R

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationsFragment()
    }
}