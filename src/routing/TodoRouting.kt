package id.panicLabs.routing

import id.panicLabs.data.todo.Todo
import id.panicLabs.service.TodoService
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

fun Route.todo(todoService: TodoService) {

    route("/todos") {

        get("/") {
            call.respondText(
                contentType = ContentType.Application.Json,
                text = Json.stringify(Todo.serializer().list, todoService.getAllTodo())
            )
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val todo = todoService.findById(id)

            if (todo == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(todo)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            call.respond(if(todoService.deleteById(id)) HttpStatusCode.OK else HttpStatusCode.NotFound)
        }

        post("/newTodo") {

            val newTodo = todoService.insert(call.receive())

            if (newTodo != null)
                call.respond(HttpStatusCode.OK, newTodo)
            else
                call.respond(HttpStatusCode.BadRequest)
        }

        put("/edit/{id}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val updatedTodo = todoService.update(id, call.receive())

            if (updatedTodo == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            call.respond(HttpStatusCode.OK, updatedTodo)
        }

    }

    route("/users/{userId}/todos") {

        get("/") {
            val userId = call.parameters["userId"]?.toInt()

            if (userId == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respondText(
                contentType = ContentType.Application.Json,
                text = Json.stringify(Todo.serializer().list, todoService.findByUserId(userId))
            )
        }

        delete("/") {
            val userId = call.parameters["userId"]?.toInt()

            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            call.respond(if (todoService.deleteByUserId(userId)) HttpStatusCode.OK else HttpStatusCode.NotFound)
        }

    }

}