package telegrambot.model

data class Chat(
        val id : Long,
        val type : String,
        val firstName : String? = null,
        val lastName : String? = null,
        val username : String? = null,
        val title : String? = null)