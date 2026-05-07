data class User(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val walletBalance: Int = 0,
    val totalKills: Int = 0,
    val totalWinnings: Int = 0
)

data class Tournament(
    val id: String = "",
    val title: String = "",
    val bannerUrl: String = "",
    val mapType: String = "",
    val entryFee: Int = 0,
    val prizePool: Int = 0,
    val perKill: Int = 0,
    val totalSlots: Int = 100,
    val joinedSlots: Int = 0,
    val matchTime: Long = 0,
    val status: String = "Upcoming" // Upcoming, Ongoing, Completed
)

data class JoinRecord(
    val uid: String = "",
    val gameUsername: String = "",
    val slotNumber: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
