package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import telegrambot.message.ModelMessageBuilder
import java.util.*

@Component
class UpdatesScheduler {
    @Autowired
    private lateinit var botController : BotController

    @Autowired
    private lateinit var footballstatProvider : FootballstatProvider

    @Autowired
    private lateinit var modelMessageBuilder : ModelMessageBuilder

    var offset : Int = -1

    @Scheduled(fixedRate = 5000)
    fun listenBot() : Unit {
        val updates = botController.getUpdates(offset)

        if (!updates.isEmpty()) {
            val update = updates.last()
            if (Objects.equals(update.message.text, "/availableleagues")) {
                val availableLeagues = footballstatProvider.availableLeagues()
                val formattedLeagues = modelMessageBuilder.getMessage(availableLeagues)

                botController.sendMessage(update.message.chat.id, formattedLeagues)
                offset = updates.last().id + 1
            }
        }
    }
}