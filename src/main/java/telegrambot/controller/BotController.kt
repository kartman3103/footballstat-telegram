package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.model.Message
import telegrambot.model.Update
import telegrambot.model.User
import telegrambot.parsers.TelegramModelParser
import telegrambot.url.TelegramUrlDealer
import java.nio.charset.Charset

@Component
open class BotController {
    @Autowired
    private lateinit var telegramHttpController: HttpController

    @Autowired
    private lateinit var telegramUrlDealer: TelegramUrlDealer

    @Autowired
    private lateinit var telegramModelParser: TelegramModelParser

    open fun getMe() : User? {
        val content : String = telegramHttpController.makeContentGET(
                telegramUrlDealer.getMe, Charset.defaultCharset())

        return telegramModelParser.parseUserResponse(content)
    }

    open fun getUpdates(offset : Int) : List<Update> {
        val content : String = telegramHttpController.makeContentGET(
                "${telegramUrlDealer.getUpdates}?offset=$offset", Charset.defaultCharset())

        return telegramModelParser.parseUpdates(content)
    }

    open fun sendMessage(chatId : Long, message : String) : Message {
        val content = telegramHttpController.makeContentGET(
                "${telegramUrlDealer.sendMessage}?chat_id=$chatId&text=$message", Charset.defaultCharset())

        return telegramModelParser.parseMessageResponse(content)
    }
}