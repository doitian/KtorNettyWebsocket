package me.iany

import kotlinx.coroutines.experimental.channels.consumeEach
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.content.defaultResource
import org.jetbrains.ktor.content.resources
import org.jetbrains.ktor.content.static
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.jetty.Jetty
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.routing
import org.jetbrains.ktor.websocket.WebSockets
import org.jetbrains.ktor.websocket.webSocket
import java.time.Duration

fun main(args:Array<String>) {
    val server = embeddedServer(Jetty, 3001) {
        install(WebSockets) {
            pingPeriod = Duration.ofMinutes(1)
        }
        routing {
            webSocket("/ws") {
                println("connected")
                try {
                    incoming.consumeEach { frame ->
                        println(frame)
                    }
                } finally {
                    println("closed")
                }
            }

            static {
                defaultResource("index.html", "web")
                resources("web")
            }
        }
    }

    println("start")
    try {
        server.start(wait=true)
    } finally {
        println("terminated")
    }
}