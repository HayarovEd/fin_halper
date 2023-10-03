package appk.obzorkards.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appk.obzorkards.R
import appk.obzorkards.adapter.LoansAdapter
import appk.obzorkards.databinding.LoansFragmentBinding
import appk.obzorkards.model.LoanModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoansFragment : BaseFragment() {

    private lateinit var adapter: LoansAdapter
    private var _binding: LoansFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoansFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { goBack() }

        adapter = LoansAdapter({
            val bundle = Bundle()
            bundle.putSerializable("data", it)
            findNavController().navigate(R.id.loanDetailFragment, bundle)
        }, {
            val bundle = Bundle()
            bundle.putString("url", it.url)
            findNavController().navigate(R.id.browserFragment, bundle)
        })
        binding.rvLoans.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvLoans.hasFixedSize()
        binding.rvLoans.adapter = adapter

        fetchData()
    }

    private fun fetchData(){
        val call = request.getLoans()
        call.enqueue(object : Callback<List<LoanModel>> {
            override fun onResponse(
                call: Call<List<LoanModel>>,
                response: Response<List<LoanModel>>
            ) {
                response.body()?.let { adapter.setList(it) }
            }

            override fun onFailure(call: Call<List<LoanModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}