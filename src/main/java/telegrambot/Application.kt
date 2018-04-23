package telegrambot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
open class Application {
    companion object {
        @JvmStatic fun main(args : Array<String>) {
            SpringApplication.run(arrayOf(Application::class.java), args)
        }
    }
}