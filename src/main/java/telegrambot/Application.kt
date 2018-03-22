package telegrambot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@ComponentScan
@Configuration
open class Application {
    fun main(args : Array<String>) {
        SpringApplication.run(arrayOf(Application::class.java), args)
    }
}