package com.youssef.musictask.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.youssef.musictask.R

abstract class BaseFragment<P> : Fragment(), MvpViewUtils {
    protected abstract val presenter: P
    private var progressBar: ProgressBar? = null
    private var mContainer: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContainer = container
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState, view)
    }

    protected abstract fun initViews(savedInstanceState: Bundle?, view: View)

    override fun onNetworkError() {
        Toast.makeText(requireActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT)
            .show()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}