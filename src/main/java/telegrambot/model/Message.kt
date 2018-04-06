package telegrambot.model

class Message(val messageId : Int, val messageDate : Int) {
    val id : Int = messageId

    val date : Int = messageDate

    var from : User? = null

    var chat : Chat? = null
}