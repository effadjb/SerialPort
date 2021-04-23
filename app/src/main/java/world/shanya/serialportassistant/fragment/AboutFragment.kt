package world.shanya.serialportassistant.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import world.shanya.serialportassistant.BuildConfig
import world.shanya.serialportassistant.MyViewModel
import world.shanya.serialportassistant.R
import java.util.*


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_about, container, false)
        val myViewModel =
            ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[MyViewModel::class.java]
        val copyRightsElement = Element()
        val copyrights = String.format(requireActivity().getString(R.string.copy_right),
            Calendar.getInstance().get(Calendar.YEAR))
        copyRightsElement.title = copyrights
//        copyRightsElement.iconDrawable = R.drawable.about_icon_copy_right
//        copyRightsElement.autoApplyIconTint = true
//        copyRightsElement.iconTint = mehdi.sakout.aboutpage.R.color.about_item_icon_color
//        copyRightsElement.iconNightTint = android.R.color.white
        copyRightsElement.gravity = Gravity.CENTER
        copyRightsElement.onClickListener =
            View.OnClickListener {
                Toast.makeText(requireActivity(), copyrights, Toast.LENGTH_SHORT).show()
            }

        val versionElement = Element()
        versionElement.title = "Version: ${BuildConfig.VERSION_NAME}"
        versionElement.gravity = Gravity.CENTER
        versionElement.setOnClickListener {
            myViewModel.checkUpdate()
        }

        return AboutPage(context)
            .isRTL(false)
            .enableDarkMode(false)
            .setImage(R.mipmap.icon)

            .setDescription("这是一款手机蓝牙串口助手APP，可以帮助我们使用手机快速的调试串口，也可以当作蓝牙遥控器来使用。" +
                    "支持常规通信页面、矩阵键盘以及自定义键盘。")
            .addGroup("联系我")
            .addEmail("shanyaliux@qq.com", "邮箱")
            .addWebsite("www.shanya.world","个人主页")
            .addGitHub("Shanyaliux")
            .addItem(versionElement)
            .addItem(copyRightsElement)
            .create()
    }

}