package appk.obzorkards.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import appk.obzorkards.R
import appk.obzorkards.databinding.BrowserFragmentBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class BrowserFragment : BaseFragment() {

    private val multiple_files = true
    private var cam_file_data: String? = null
    private var file_data: ValueCallback<Uri>? = null
    private var file_path: ValueCallback<Array<Uri>>? = null

    private var _binding: BrowserFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BrowserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { goBack() }

        val url = arguments?.getString("url")

        binding.webview.settings.let {
            it.javaScriptEnabled = true
            it.domStorageEnabled = true
            it.databaseEnabled = true
            it.allowContentAccess = true
            it.loadWithOverviewMode = true
            it.useWideViewPort = true
            it.javaScriptCanOpenWindowsAutomatically = true
            it.saveFormData = true
            //it.setAppCacheEnabled(true)
            it.cacheMode = WebSettings.LOAD_NO_CACHE
            it.allowFileAccess = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.mixedContentMode = 0
            }
        }
        binding.webview.requestFocus(View.FOCUS_DOWN)
        binding.webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        binding.webview.webViewClient = object : WebViewClient() {

            @Deprecated("Deprecated in Java")
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {}

            override fun onPageFinished(view: WebView, url: String) {}

            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                when {
                    url.startsWith("mailto:") -> {
                        startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse(url)))
                    }
                    url.startsWith("tel:") -> {
                        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                    }
                    url.startsWith("tg://") -> {
                        startActivity(telegramIntent(requireContext(), url))
                    }
                    url.startsWith("viber://") -> {
                        startActivity(viberIntent(requireContext(), url))
                    }
                    else -> {
                        view.loadUrl(url)
                    }
                }
                return true
            }

            @TargetApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                when {
                    uri.toString().startsWith("mailto:") -> {
                        startActivity(Intent(Intent.ACTION_SENDTO, uri))
                    }
                    uri.toString().startsWith("tel:") -> {
                        startActivity(Intent(Intent.ACTION_DIAL, uri))
                    }
                    uri.toString().startsWith("tg://") -> {
                        startActivity(this.let {
                            telegramIntent(
                                requireContext(),
                                uri.toString()
                            )
                        })
                    }
                    uri.toString().startsWith("viber://") -> {
                        startActivity(this.let { viberIntent(requireContext(), uri.toString()) })
                    }
                    else -> {
                        view.loadUrl(uri.toString())
                    }
                }
                return true
            }
        }

        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {

                if (file_permission() && Build.VERSION.SDK_INT >= 21) {
                    file_path = filePathCallback
                    var takePictureIntent: Intent? = null
                    var takeVideoIntent: Intent? = null

                    var includeVideo = false
                    var includePhoto = false

                    paramCheck@ for (acceptTypes in fileChooserParams.acceptTypes) {
                        val splitTypes =
                            acceptTypes.split(", ?+".toRegex()).dropLastWhile { it.isEmpty() }
                                .toTypedArray()
                        for (acceptType in splitTypes) {
                            when (acceptType) {
                                "*/*" -> {
                                    includePhoto = true
                                    includeVideo = true
                                    break@paramCheck
                                }
                                "image/*" -> includePhoto = true
                                "video/*" -> includeVideo = true
                            }
                        }
                    }

                    if (fileChooserParams.acceptTypes.isEmpty()) {
                        includePhoto = true
                        includeVideo = true
                    }

                    if (includePhoto) {
                        takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (requireActivity().packageManager?.let { takePictureIntent?.resolveActivity(it) } != null) {
                            var photoFile: File? = null
                            try {
                                photoFile = create_image()
                                takePictureIntent.putExtra("PhotoPath", cam_file_data)
                            } catch (ex: IOException) {
                                Log.e("TAG", "Image file creation failed", ex)
                            }

                            if (photoFile != null) {
                                cam_file_data = "file:" + photoFile.absolutePath
                                takePictureIntent.putExtra(
                                    MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(photoFile)
                                )
                            } else {
                                cam_file_data = null
                                takePictureIntent = null
                            }
                        }
                    }

                    if (includeVideo) {
                        takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                        if (requireActivity().packageManager?.let { takeVideoIntent?.resolveActivity(it) } != null) {
                            var videoFile: File? = null
                            try {
                                videoFile = create_video()
                            } catch (ex: IOException) {
                                Log.e("TAG", "Video file creation failed", ex)
                            }

                            if (videoFile != null) {
                                cam_file_data = "file:" + videoFile.absolutePath
                                takeVideoIntent.putExtra(
                                    MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(videoFile)
                                )
                            } else {
                                cam_file_data = null
                                takeVideoIntent = null
                            }
                        }
                    }

                    val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                    contentSelectionIntent.type = file_type
                    if (multiple_files) {
                        contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    }

                    val intentArray: Array<Intent>
                    if (takePictureIntent != null && takeVideoIntent != null) {
                        intentArray = arrayOf(takePictureIntent, takeVideoIntent)
                    } else if (takePictureIntent != null) {
                        intentArray = arrayOf(takePictureIntent)
                    } else if (takeVideoIntent != null) {
                        intentArray = arrayOf(takeVideoIntent)
                    } else {
                        intentArray = arrayOfNulls<Intent>(0) as Array<Intent>
                    }

                    val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                    chooserIntent.putExtra(Intent.EXTRA_TITLE, "File chooser")
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                    startActivityForResult(
                        chooserIntent,
                        file_req_code
                    )
                    return true
                } else {
                    return false
                }
            }
        }

        binding.webview.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    val webView = v as WebView

                    when (keyCode) {
                        KeyEvent.KEYCODE_BACK -> if (webView.canGoBack()) {
                            webView.goBack()
                            return true
                        }
                    }
                }

                return false
            }
        })

        url?.let { binding.webview.loadUrl(it) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (Build.VERSION.SDK_INT >= 21) {
            var results: Array<Uri>? = null

            /*-- if file request cancelled; exited camera. we need to send null value to make future attempts workable --*/
            if (resultCode == Activity.RESULT_CANCELED) {
                if (requestCode == file_req_code) {
                    file_path!!.onReceiveValue(null)
                    return
                }
            }

            /*-- continue if response is positive --*/
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == file_req_code) {
                    if (null == file_path) {
                        return
                    }

                    var clipData: ClipData?
                    var stringData: String?
                    try {
                        clipData = intent!!.clipData
                        stringData = intent.dataString
                    } catch (e: Exception) {
                        clipData = null
                        stringData = null
                    }

                    if (clipData == null && stringData == null && cam_file_data != null) {
                        results = arrayOf(Uri.parse(cam_file_data))
                    } else {
                        if (clipData != null) { // checking if multiple files selected or not
                            val numSelectedFiles = clipData.itemCount
                            results = arrayOfNulls<Any>(numSelectedFiles) as Array<Uri>
                            for (i in 0 until clipData.itemCount) {
                                results[i] = clipData.getItemAt(i).uri
                            }
                        } else {
                            results = arrayOf(Uri.parse(stringData))
                        }
                    }
                }
            }
            file_path!!.onReceiveValue(results)
            file_path = null
        } else {
            if (requestCode == file_req_code) {
                if (null == file_data) return
                val result =
                    if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data
                file_data!!.onReceiveValue(result)
                file_data = null
            }
        }
    }

    private fun file_permission(): Boolean {
        if (Build.VERSION.SDK_INT >= 23 && (this.let {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                1
            )
            return false
        } else {
            return true
        }
    }

    @Throws(IOException::class)
    private fun create_image(): File {
        @SuppressLint("SimpleDateFormat") val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "img_" + timeStamp + "_"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    @Throws(IOException::class)
    private fun create_video(): File {
        @SuppressLint("SimpleDateFormat")
        val file_name = SimpleDateFormat("yyyy_mm_ss").format(Date())
        val new_name = "file_" + file_name + "_"
        val sd_directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(new_name, ".3gp", sd_directory)
    }


    private fun telegramIntent(context: Context, url: String): Intent {
        var intent: Intent? = null
        try {
            try {
                context.packageManager.getPackageInfo("org.telegram.messenger", 0)
            } catch (e: Exception){
                context.packageManager.getPackageInfo("org.thunderdog.challegram", 0)
            }
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }catch (e: Exception){ //App not found open in browser
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
        return intent!!
    }

    private fun viberIntent(context: Context, url: String): Intent {
        var intent: Intent? = null
        try {
            try {
                context.packageManager.getPackageInfo("com.viber.voip", 0)
            } catch (e: Exception){
                context.packageManager.getPackageInfo("com.viber.voip", 0)
            }
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }catch (e: Exception){ //App not found open in browser
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        }
        return intent!!
    }


    companion object {
        private const val file_type = "image/*" // file types to be allowed for upload
        private const val file_req_code = 1
    }
}