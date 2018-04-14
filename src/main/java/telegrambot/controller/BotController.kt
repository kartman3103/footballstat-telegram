package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.url.TelegramUrlDealer
import telegrambot.model.Message
import telegrambot.model.Update
import telegrambot.model.User
import telegrambot.parsers.TelegramModelParser
import java.nio.charset.Charset

@Component
open class BotController {
    @Autowired
    private lateinit var httpController : HttpController

    @Autowired
    private lateinit var telegramUrlDealer: TelegramUrlDealer

    @Autowired
    private lateinit var telegramModelParser: TelegramModelParser

    open fun getMe() : User? {
        val content : String = httpController.makeContentGET(
                telegramUrlDealer.getMe, Charset.defaultCharset())

        return telegramModelParser.parseUserResponse(content)
    }

    open fun getUpdates() : List<Update> {
        val content : String = httpController.makeContentGET(
                telegramUrlDealer.getUpdates, Charset.defaultCharset())

        return telegramModelParser.parseUpdates(content)
    }

    open fun sendMessage(chatId : Int, message : String) : Message {
        val content = httpController.makeContentGET(
                "${telegramUrlDealer.sendMessage}?chat_id=$chatId&text=$message", Charset.defaultCharset())

        return telegramModelParser.parseMessageResponse(content)
    }
}