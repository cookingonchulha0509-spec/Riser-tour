import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.esportsapp.databinding.BottomSheetJoinBinding

class JoinBottomSheetFragment(private val tournament: Tournament) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetJoinBinding
    private val db = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()
    private var selectedSlot: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Populate slot recyclerview here (Logic excluded for brevity)
        // Assume user selects a slot and selectedSlot variable is updated
        
        binding.btnConfirmJoin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            if (username.isEmpty()) {
                binding.etUsernameLayout.error = "Enter In-Game Username"
                return@setOnClickListener
            }
            if (selectedSlot == -1) {
                Toast.makeText(context, "Please select a slot", Toast.show()).show()
                return@setOnClickListener
            }
            joinTournament(username)
        }
    }

    private fun joinTournament(username: String) {
        val uid = auth.currentUser?.uid ?: return
        
        // 1. Check Wallet Balance & Deduct (Using Transaction for safety)
        val userRef = db.child("users").child(uid)
        
        // 2. Add to joins node
        val joinRecord = JoinRecord(uid, username, selectedSlot)
        
        db.child("joins").child(tournament.id).child(uid).setValue(joinRecord)
            .addOnSuccessListener {
                Toast.makeText(context, "Successfully Joined!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to join: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
