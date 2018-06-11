package com.beachpartnerllc.beachpartner.fragments

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.beachpartnerllc.beachpartner.R

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Jun 2018 at 5:10 PM
 */
class ImageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        (view as ImageView).setImageResource(arguments!!.getInt(ARG_IMAGE_RES_ID))
        return view
    }

    companion object {
        private const val ARG_IMAGE_RES_ID = "arg_image_res_id"

        fun newInstance(@DrawableRes resId: Int): ImageFragment {
            val args = Bundle()
            args.putInt(ARG_IMAGE_RES_ID, resId)
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}