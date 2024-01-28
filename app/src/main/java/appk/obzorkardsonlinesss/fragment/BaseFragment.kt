package appk.obzorkardsonlinesss.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import appk.obzorkardsonlinesss.network.ApiService
import appk.obzorkardsonlinesss.network.ServiceBuilder


abstract class BaseFragment : Fragment() {

    val request = ServiceBuilder.buildService(ApiService::class.java)


    fun goBack() { findNavController().navigateUp() }
}