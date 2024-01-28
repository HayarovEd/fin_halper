package appk.obzorkardsonlinesss.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import appk.obzorkardsonlinesss.databinding.ActivityMainBinding
import appk.obzorkardsonlinesss.R
import com.pixplicity.easyprefs.library.Prefs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        val isAccept = Prefs.getBoolean("accepted", false)
        Log.d ("ASDFG", "accepted - $isAccept")
        if (isAccept){
            navController.navigate(R.id.mainFragment)
        } else {
            navController.navigate(R.id.startFragment)
           // graph.startDestination = R.id.startFragment
        }
       //navHostFragment.navController.graph = graph
    }
}