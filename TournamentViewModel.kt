import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class TournamentViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    
    private val _tournaments = MutableLiveData<List<Tournament>>()
    val tournaments: LiveData<List<Tournament>> get() = _tournaments

    init {
        fetchTournaments()
    }

    private fun fetchTournaments() {
        db.child("tournaments").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Tournament>()
                for (snap in snapshot.children) {
                    val tournament = snap.getValue(Tournament::class.java)
                    tournament?.let { list.add(it) }
                }
                _tournaments.value = list
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
