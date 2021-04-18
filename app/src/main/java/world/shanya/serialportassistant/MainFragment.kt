package world.shanya.serialportassistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import world.shanya.serialportassistant.fragment.KeyboardFragment
import world.shanya.serialportassistant.fragment.MessageFragment
import world.shanya.serialportassistant.fragment.TerminalFragment


class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3
            override fun createFragment(position: Int) =
                when (position) {
                    0 -> MessageFragment()
                    1 -> KeyboardFragment()
                    else -> TerminalFragment()
                }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "通信"
                1 -> "矩阵键盘"
                else -> "自定义键盘"
            }
        }.attach()
    }
}