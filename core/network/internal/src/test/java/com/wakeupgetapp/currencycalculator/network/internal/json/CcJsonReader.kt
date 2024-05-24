package com.wakeupgetapp.currencycalculator.network.internal.json

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class CcJsonReader : (String) -> String {
    private val path = "src/test/java/com/wakeupgetapp/currencycalculator/network/internal/json/"

    override fun invoke(filePath: String): String {
        val file = File(path + filePath)
        val stringBuilder = StringBuilder()

        if (file.exists()) {
            BufferedReader(FileReader(file)).use { reader ->
                var line = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line).append('\n')
                    line = reader.readLine()
                }
            }
        } else {
            throw IllegalArgumentException("File $filePath not exist.")
        }

        return stringBuilder.toString()

    }
}