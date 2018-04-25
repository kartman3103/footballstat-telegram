package telegrambot.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
open class UpdatesScheduler {
    private val logger = LoggerFactory.getLogger(UpdatesScheduler::class.java)

    @Autowired
    private lateinit var botController : BotController

    @Autowired
    private lateinit var updateProcessor : UpdateProcessor

    var offset : Int = -1

    @Scheduled(fixedRate = 3000)
    fun listenBot() : Unit {
        botController.getUpdates(offset).forEach {
            logger.info("Received a update. Update " +
                    "id: ${it.id}. Message:{ " +
                        "id: ${it.message.id}, " +
                        "date: ${it.message.date}, chat: ${it.message.chat}, " +
                        "text: ${it.message.text}, from: ${it.message.from}")

            botController.sendMessage(it.message.chat.id, updateProcessor.proccessUpdate(it))
            offset = it.id + 1
        }
    }
}