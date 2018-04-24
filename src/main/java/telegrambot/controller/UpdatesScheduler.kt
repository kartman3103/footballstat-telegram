package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
open class UpdatesScheduler {
    @Autowired
    private lateinit var botController : BotController

    @Autowired
    private lateinit var updateProcessor : UpdateProcessor

    var offset : Int = -1

    @Scheduled(fixedRate = 3000)
    fun listenBot() : Unit {
        botController.getUpdates(offset).forEach {
            botController.sendMessage(it.message.chat.id, updateProcessor.proccessUpdate(it))
            offset = it.id + 1
        }
    }
}