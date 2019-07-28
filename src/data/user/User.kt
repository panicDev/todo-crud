package id.panicLabs.data.user

import id.panicLabs.util.serializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class User(
    val id: Int,
    val name: String,
    val surname: String,
    @Serializable(with = DateTimeSerializer::class)
    val birthDate: DateTime?
)