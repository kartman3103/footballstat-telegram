package telegrambot.model.football

data class TLeague(
        val name : String,
        val matchDay : Int,
        var teams : List<TTeam> = emptyList())