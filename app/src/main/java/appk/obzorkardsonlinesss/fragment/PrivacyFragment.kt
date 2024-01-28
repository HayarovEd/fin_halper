package appk.obzorkardsonlinesss.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appk.obzorkardsonlinesss.databinding.PrivacyFragmentBinding
import appk.obzorkardsonlinesss.model.PrivacyModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PrivacyFragment : BaseFragment() {

    private var _binding: PrivacyFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PrivacyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { goBack() }

        fetchData()
    }

    private fun fetchData(){
        val call = request.getPrivacyPolicy()
        call.enqueue(object : Callback<List<PrivacyModel>>{
            override fun onResponse(
                call: Call<List<PrivacyModel>>,
                response: Response<List<PrivacyModel>>
            ) {
//                textPrivacy.text = Html.fromHtml(response.body()?.get(0)?.text)
                binding.textPrivacy.text = response.body()?.get(0)?.text
            }

            override fun onFailure(call: Call<List<PrivacyModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}