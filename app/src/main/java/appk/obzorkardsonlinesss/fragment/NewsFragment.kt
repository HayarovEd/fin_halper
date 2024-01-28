package appk.obzorkardsonlinesss.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appk.obzorkardsonlinesss.R
import appk.obzorkardsonlinesss.adapter.NewsAdapter
import appk.obzorkardsonlinesss.databinding.NewsFragmentBinding
import appk.obzorkardsonlinesss.model.NewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : BaseFragment() {

    private lateinit var adapter: NewsAdapter

    private var _binding: NewsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { goBack() }

        adapter = NewsAdapter{
            val bundle = Bundle()
            bundle.putSerializable("data", it)
            findNavController().navigate(R.id.newsDetailFragment, bundle)
        }
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvNews.hasFixedSize()
        binding.rvNews.adapter = adapter

        fetchData()
    }

    private fun fetchData(){

        val call = request.getNews()
        call.enqueue(object : Callback<List<NewsModel>> {
            override fun onResponse(
                call: Call<List<NewsModel>>,
                response: Response<List<NewsModel>>
            ) {
                response.body()?.let { adapter.setList(it) }
            }

            override fun onFailure(call: Call<List<NewsModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}