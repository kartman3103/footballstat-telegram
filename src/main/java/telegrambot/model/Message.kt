package telegrambot.model

data class Message(
        val id : Long,
        val date : Int,
        val chat : Chat,
        val text : String? = null,
        val from : User? = null)