import com.squareup.moshi.*
import java.io.*

fun main() {
    val f = ObjectInputStream(FileInputStream("Rutes.obj"))
    val llistaDePunts = arrayListOf<PuntGeo>()
    val llistaRutes = arrayListOf<Ruta>()

    val moshi = Moshi.Builder().build()
    val llistaTipus = Types.newParameterizedType(List::class.java, Ruta::class.java)
    val adapter: JsonAdapter<List<Ruta>> = moshi.adapter(llistaTipus)



    try {
        while (true) {
            val e = f.readObject () as Ruta

            val numPunts = e.size()
            for (i in 0 until numPunts) {
                val llistaTipusPuntGeo = Types.newParameterizedType(List::class.java, PuntGeo::class.java)
                val adapterPuntGeo: JsonAdapter<List<PuntGeo>> = moshi.adapter(llistaTipusPuntGeo)

                llistaDePunts.add(PuntGeo(e.getPuntNom(i),Coordenades(e.getPuntLatitud(i),e.getPuntLongitud(i))))
            }

            llistaRutes.add(Ruta(e.nom, e.desnivell, e.desnivellAcumulat, llistaDePunts))
        }

    } catch (eof: EOFException) {
        f.close()
    }
        val json = adapter.toJson(llistaRutes)
    File("Rutes.json").writeText(json)
}
