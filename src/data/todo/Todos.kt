package id.panicLabs.data.todo

import id.panicLabs.data.user.Users
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Todos : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val userId = integer("userId").references(Users.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val title = text("title")
    val notes = text("notes")
    val dateCreated = date("dateCreated")
}