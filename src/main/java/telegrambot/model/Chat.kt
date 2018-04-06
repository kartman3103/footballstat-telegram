package telegrambot.model

class Chat(val chatId : Int, val chatType : String) {
    val id : Int = chatId

    val type : String = chatType

    var title : String? = null

    var username : String? = null

    var firstName : String? = null

    var lastName : String? = null
}