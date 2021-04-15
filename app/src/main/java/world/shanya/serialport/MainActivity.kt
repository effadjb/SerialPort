package world.shanya.serialport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import world.shanya.serialport.fragment.KeyboardFragment
import world.shanya.serialport.fragment.MessageFragment
import world.shanya.serialport.fragment.TerminalFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3
            override fun createFragment(position: Int) =
                    when (position) {
                        0 -> MessageFragment()
                        1 -> KeyboardFragment()
                        else -> TerminalFragment()
                    }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                title = when (tab?.position) {
                    0 -> "Message"
                    1 -> "Keyboard"
                    else -> "Terminal"
                }
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->

        }.attach()
    }
}