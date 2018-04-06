package telegrambot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Application {
    fun main(args : Array<String>) {
        SpringApplication.run(arrayOf(Application::class.java), args)
    }
}