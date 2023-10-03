package appk.obzorkards.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appk.obzorkards.R
import appk.obzorkards.adapter.CardsAdapter
import appk.obzorkards.databinding.CardsFragmentBinding
import appk.obzorkards.model.CardModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardsFragment : BaseFragment() {

    private lateinit var adapter: CardsAdapter

    private var _binding: CardsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CardsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { goBack() }

        adapter = CardsAdapter{
            val bundle = Bundle()
            bundle.putString("url", it.url)
            findNavController().navigate(R.id.browserFragment, bundle)
        }
        binding.rvCards.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvCards.hasFixedSize()
        binding.rvCards.adapter = adapter

        fetchData()
    }

    private fun fetchData(){
        val call = request.getCards()
        call.enqueue(object : Callback<List<CardModel>> {
            override fun onResponse(
                call: Call<List<CardModel>>,
                response: Response<List<CardModel>>
            ) {
                response.body()?.let { adapter.setList(it) }
            }

            override fun onFailure(call: Call<List<CardModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}