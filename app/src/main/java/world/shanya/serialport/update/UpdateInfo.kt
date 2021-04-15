package world.example.serialport.update

data class UpdateInfo(
    val versionCode: Int,
    val versionName: String,
    val fileName: String,
    val mandatoryUpdate: Int,
    val updateContent: String,
    val downloadUrl: String
)
