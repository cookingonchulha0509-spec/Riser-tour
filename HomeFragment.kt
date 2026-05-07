import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.esportsapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TournamentViewModel
    private lateinit var adapter: TournamentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        
        viewModel = ViewModelProvider(this)[TournamentViewModel::class.java]

        setupRecyclerView()
        observeTournaments()
    }

    private fun setupRecyclerView() {
        adapter = TournamentAdapter(emptyList(), 
            onJoinClicked = { tournament ->
                val bottomSheet = JoinBottomSheetFragment(tournament)
                bottomSheet.show(parentFragmentManager, "JoinBottomSheet")
            },
            onItemClicked = { tournament ->
                val intent = Intent(requireContext(), TournamentDetailsActivity::class.java)
                intent.putExtra("TOURNAMENT_ID", tournament.id)
                startActivity(intent)
            }
        )
        
        binding.rvTournaments.layoutManager = LinearLayoutManager(context)
        binding.rvTournaments.adapter = adapter
    }

    private fun observeTournaments() {
        viewModel.tournaments.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)
        }
    }
}
