package hr.ferit.pcbuildlogger.ui.tabs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import hr.ferit.pcbuildlogger.R
import hr.ferit.pcbuildlogger.databinding.ActivityTabsBinding


class TabsActivity : AppCompatActivity() {

    lateinit var tabsBinding: ActivityTabsBinding
    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabsBinding = ActivityTabsBinding.inflate(layoutInflater)

        setContentView(tabsBinding.root)

        tabLayout = findViewById(R.id.tabLayout)

        viewPagerAdapter = TabAdapter(this, supportFragmentManager)
        for (i in 0 until viewPagerAdapter.count) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPagerAdapter.getPageTitle(i)))
        }
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}