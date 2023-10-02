
import com.squareup.moshi.*
import java.io.*


fun main (args: Array<String>){
    val inputFile = "Rutes.obj"
    val outputFile = "Rutes.json"

    val input = ObjectInputStream(FileInputStream(inputFile))

    val moshi = Moshi.Builder().build()

    val rutas = mutableListOf<Ruta>()

    try {
        while (true) {
            val ruta = input.readObject() as Ruta
            rutas.add(ruta)
        }
    } catch (eof: EOFException) {
        input.close()
    }

    val json = moshi.adapter(List::class.java).toJson(rutas)

    val fileWriter = FileWriter(outputFile)
    fileWriter.use {
        it.write(json)
    }

}