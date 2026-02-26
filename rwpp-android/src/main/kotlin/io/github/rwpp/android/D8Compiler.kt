package io.github.rwpp.android

import android.annotation.SuppressLint
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import java.io.File

object D8Compiler {

    @SuppressLint("SetWorldReadable")
    fun compileToDex(
        jarFile: File,
        outputDir: File
    ) {
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        val command = D8Command.builder()
            .addProgramFiles(jarFile.toPath())
            .setOutput(outputDir.toPath(), OutputMode.DexIndexed)
            .setMinApiLevel(21)
            .build()

        D8.run(command)

        val dexFiles = outputDir.listFiles { file -> file.name.endsWith(".dex") }
        if (dexFiles != null) {
            for (dexFile in dexFiles) {
                dexFile.setWritable(false, false)
                dexFile.setReadable(true, false)
            }
        }
    }
}
