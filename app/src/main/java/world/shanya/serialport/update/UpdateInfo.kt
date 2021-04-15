package world.shanya.serialport.update

data class UpdateInfo(
    val apkVersionCode: Int,
    val apkVersionName: String,
    val apkName: String,
    val apkSize: String,
    val forcedUpgrade: Int,
    val apkDescription: String,
    val apkUrl: String,
    val apkMD5: String
)
