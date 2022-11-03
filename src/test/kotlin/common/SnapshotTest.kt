package common

import common.HETAMarshaller.Companion.toJSON
import preprocessor.python3preprocessor.CSharpPreprocessor
import java.io.File

interface SnapshotTest {


    fun filepathOf(language: String, filename: String): String = "src/test/kotlin/${language}/files/${filename}"

    fun snapshotFilepathOf(language: String, filename: String) = filepathOf("${language}", "${filename}.json")

    fun snapshot(language: String, filename: String) {
        File(filepathOf(language, filename)).readText().let { src ->
            CSharpPreprocessor(arrayOf()).tryToHetas(src)?.toJSON(snapshotFilepathOf(language, filename))
        }

    }


}