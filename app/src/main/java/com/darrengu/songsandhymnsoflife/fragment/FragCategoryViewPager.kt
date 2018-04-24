package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterPagerCategory
import kotlinx.android.synthetic.main.fragment_category_pager.*
import org.jetbrains.anko.bundleOf

/**
 * Created by darren.gu on 3/8/18.
 */
class FragCategoryViewPager : BaseFragmentMainActivity() {
    companion object {
        private const val CATEGORY_ID = "categoryId"

        fun newInstance(id: Long): FragCategoryViewPager {
            val fragment = FragCategoryViewPager()
            fragment.arguments = bundleOf(CATEGORY_ID to id)
            return fragment
        }
    }

    private var categoryId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getLong(CATEGORY_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryList.layoutManager = LinearLayoutManager(context)

        viewModel.categories.observe(this, Observer {
            it?.get(categoryId)?.let {
                categoryList.adapter = AdapterPagerCategory(it) {
                    Log.d("onClickSong", it.toString())
                }
            }
            Log.d("result", it?.size.toString())
        })

        categoryId?.let { viewModel.findSongsInCategory(it) }

    }
}