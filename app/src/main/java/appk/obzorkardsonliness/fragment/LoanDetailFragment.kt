package appk.obzorkardsonliness.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import appk.obzorkardsonliness.R
import appk.obzorkardsonliness.databinding.LoansDetailFragmentBinding
import appk.obzorkardsonliness.model.LoanModel
import com.bumptech.glide.Glide

class LoanDetailFragment : BaseFragment() {

    private var _binding: LoansDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoansDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { goBack() }

        val data = arguments?.getSerializable("data") as LoanModel

        Glide.with(requireContext()).load(data.imageUrl).into(binding.image)

        binding.percentage.text = "от ${data.percent} в день"
        binding.summa.text = "до ${data.sumOne} в день"
        binding.days.text = "до ${data.termTo} дней"

        binding.time.text = "Прием заявок: ${data.workTime}"
        binding.speed.text = "Время принятия решения: ${data.scorost}"
        binding.documents.text = "Документы для подачи заявки: ${data.dokuments}"
        binding.way.text = "Способ подачи заявки: ${data.sposob}"
        binding.email.text = "Email: ${data.email}"
        binding.address.text = "Адресс: ${data.address}"
        binding.license.text = "Лицензия: ${data.license}"

        binding.btnGetLoan.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", data.url)
            findNavController().navigate(R.id.browserFragment, bundle)
        }
    }
}