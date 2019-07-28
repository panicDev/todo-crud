package id.panicLabs.data.todo

import id.panicLabs.util.serializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class Todo(
    val id: Int,
    val userId: Int,
    val title: String,
    val notes: String,
    @Serializable(with = DateTimeSerializer::class)
    val dateCreated: DateTime
)