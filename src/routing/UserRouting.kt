package id.panicLabs.routing

import id.panicLabs.data.user.NewUser
import id.panicLabs.data.user.User
import id.panicLabs.service.UserService
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

fun Route.user(userService: UserService) {

    route("/users"){

        get("/"){
            call.respondText(Json.stringify(User.serializer().list, userService.getAll()), contentType = ContentType.Application.Json)
        }

        get("/{id}"){
            val user = userService.findById(call.parameters["id"]!!.toInt())
            if(user == null) call.respond(HttpStatusCode.NotFound) else call.respond(user)
        }

        post("/newUser"){
            val user = call.receive<NewUser>()
            call.respond(HttpStatusCode.Created, userService.insert(user))
        }

        put("/{id}"){
            val user = call.receive<NewUser>()
            val updated = userService.updateUser(call.parameters["id"]?.toIntOrNull(), user)
            if(updated == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(HttpStatusCode.OK, updated)
        }

        delete("/{id}"){
            val removed = userService.deleteUser(call.parameters["id"]?.toInt()!!)
            call.respond(if (removed) HttpStatusCode.OK else HttpStatusCode.NotFound)
        }

    }

}