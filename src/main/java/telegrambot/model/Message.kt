package telegrambot.model

data class Message(
        val id : Long,
        val date : Int,
        val chat : Chat? = null,
        val text : String? = null,
        val from : User? = null)