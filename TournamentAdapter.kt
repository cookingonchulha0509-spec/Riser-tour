import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esportsapp.databinding.ItemTournamentBinding

class TournamentAdapter(
    private var tournaments: List<Tournament>,
    private val onJoinClicked: (Tournament) -> Unit,
    private val onItemClicked: (Tournament) -> Unit
) : RecyclerView.Adapter<TournamentAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTournamentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tournament: Tournament) {
            binding.tvTitle.text = tournament.title
            binding.tvPrizePool.text = "₹${tournament.prizePool}"
            binding.tvEntryFee.text = "₹${tournament.entryFee}"
            binding.chipStatus.text = tournament.status
            
            Glide.with(binding.root.context)
                .load(tournament.bannerUrl)
                .centerCrop()
                .into(binding.ivBanner)

            binding.btnJoin.setOnClickListener { onJoinClicked(tournament) }
            binding.root.setOnClickListener { onItemClicked(tournament) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTournamentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tournaments[position])
    }

    override fun getItemCount() = tournaments.size

    fun updateData(newList: List<Tournament>) {
        tournaments = newList
        notifyDataSetChanged() // Use DiffUtil in production for smoother animations
    }
}
