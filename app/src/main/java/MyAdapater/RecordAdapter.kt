package MyAdapater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RecordAdapter(fm:FragmentManager, var fragList:List<Fragment>):FragmentPagerAdapter(fm) {
    val title = arrayOf("Expense","Income")
    override fun getCount(): Int {
        return fragList.size

    }

    override fun getItem(position: Int): Fragment {
        return  fragList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}