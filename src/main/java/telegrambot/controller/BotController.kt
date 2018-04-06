
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.RemoteConfig
import telegrambot.controller.HttpController

@Component
open class BotController {
    @Autowired
    private lateinit var httpController : HttpController

    @Autowired
    private lateinit var remoteConfig : RemoteConfig

//    open fun getMe() : User {
//
//    }
}