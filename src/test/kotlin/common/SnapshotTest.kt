package common

import common.HETAMarshaller.Companion.toJSON
import preprocessor.Preprocessor
import java.io.File

interface SnapshotTest {

    val preprocessor: Preprocessor

    fun filepathOf(language: String, filename: String): String = "src/test/kotlin/${language}/files/${filename}"

    fun snapshotFilepathOf(language: String, filename: String) = filepathOf(language, "${filename}.json")

    fun snapshot(language: String, filename: String) {
        File(filepathOf(language, filename)).readText().let { src ->
            preprocessor.tryToHetas(src)?.toJSON(snapshotFilepathOf(language, filename))
        }

    }


}