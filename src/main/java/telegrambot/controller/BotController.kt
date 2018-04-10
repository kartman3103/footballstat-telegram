package telegrambot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import telegrambot.UrlDealer
import telegrambot.model.Update
import telegrambot.model.User
import telegrambot.parsers.ModelParser
import java.nio.charset.Charset

@Component
open class BotController {
    @Autowired
    private lateinit var httpController : HttpController

    @Autowired
    private lateinit var urlDealer : UrlDealer

    @Autowired
    private lateinit var modelParser : ModelParser

    open fun getMe() : User? {
        val content : String = httpController.makeContentGET(
                urlDealer.getMe, Charset.defaultCharset())

        return modelParser.parseUserResponse(content)
    }

    open fun getUpdates() : List<Update> {
        val content : String = httpController.makeContentGET(
                urlDealer.getUpdates, Charset.defaultCharset())

        return modelParser.parseUpdates(content)
    }

//    open fun sendMessage(chatId : Int, message : String) : Message {
//        return httpController.makeResponseGET(
//                "${urlDealer.sendMessage}?chat_id=$chatId&text=$message")
//    }
}