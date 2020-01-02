package com.adaltojunior.samplewebappkotlin

import com.adaltojunior.samplewebappkotlin.model.Course
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

var courses = listOf<Course>(
    Course(1, 1, "Course 1", true),
    Course(2, 2, "Course 2", true),
    Course(3, 3, "Course 3", true),
    Course(4, 4, "Course 5", true),
    Course(5, 5, "Course 5", true)
)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(ContentNegotiation) {
        jackson {
            enable(INDENT_OUTPUT)
        }
    }
    routing {
        get("/") {
            call.respondText("Welcome", contentType = ContentType.Text.Plain)
        }
        get("/courses") {
            call.respond(courses)
        }
        get("/course/{id}") {
            val courseId = call.parameters["id"]?.toInt()
            courseId?.let { id -> courses[id].let { course -> call.respond(course) } }
        }
    }
}

