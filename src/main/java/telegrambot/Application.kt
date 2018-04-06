package telegrambot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableAutoConfiguration
open class Application {
    fun main(args : Array<String>) {
        SpringApplication.run(arrayOf(Application::class.java), args)
    }
}