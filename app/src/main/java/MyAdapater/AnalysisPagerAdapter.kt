package MyAdapater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AnalysisPagerAdapter(val fs:FragmentManager,val fragList:List<Fragment>): FragmentPagerAdapter(fs) {
    override fun getCount(): Int {
        return fragList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragList.get(position)
    }
}