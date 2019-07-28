package id.panicLabs.routing

import id.panicLabs.service.TodoService
import id.panicLabs.service.UserService
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun Route.api(userService: UserService, todoService: TodoService) {

    route("/api") {
        user(userService)
        todo(todoService)
    }

}