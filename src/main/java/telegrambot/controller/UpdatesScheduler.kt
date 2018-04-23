package telegrambot.controller.request

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import telegrambot.controller.BotController
import java.util.*

@Component
class UpdatesScheduler {
    @Autowired
    private lateinit var botController : BotController

    var offset : Int = -1

    @Scheduled(fixedRate = 5000)
    fun listenBot() : Unit {
        val updates = botController.getUpdates(offset)

        if (!updates.isEmpty()) {
            val update = updates.last()
            if (Objects.equals(update.message.text, "/availableleagues")) {
                botController.sendMessage(update.message.chat.id, "Хуй+тебе,+залупа,+падла")
                offset = updates.last().id + 1
            }
        }
    }
}