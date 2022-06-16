package com.mansao.submissionandroidjetpackpro.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mansao.submissionandroidjetpackpro.R
import com.mansao.submissionandroidjetpackpro.ui.movie.MoviesFragment
import com.mansao.submissionandroidjetpackpro.ui.tvshow.TvShowFragment


class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
        when(position){
            0 -> MoviesFragment()
            1 -> TvShowFragment()
            else -> Fragment()
        }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence =
        mContext.resources.getString(TAB_TITLE[position])

    companion object{
        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.movies, R.string.tv_show)
    }

}