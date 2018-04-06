package telegrambot.model

class Update(val updateId : String) {
    val id : String = updateId

    var message : Message? = null
}