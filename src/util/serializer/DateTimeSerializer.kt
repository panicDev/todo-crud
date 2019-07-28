package id.panicLabs.util.serializer

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import org.joda.time.DateTime

@Serializer(forClass = DateTime::class)
object DateTimeSerializer : KSerializer<DateTime>{

    override fun deserialize(decoder: Decoder): DateTime  = DateTime.parse(decoder.decodeString())


    override fun serialize(encoder: Encoder, obj: DateTime) {
        encoder.encodeString(obj.toString())
    }

}