package ru.fefu.lesson6

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.View
import ru.fefu.lesson6.databinding.FragmentAppSpecificStorageBinding
import java.io.File

class AppSpecificStorageFragment :
    BaseFragment<FragmentAppSpecificStorageBinding>(R.layout.fragment_app_specific_storage) {

    companion object {
        private const val CACHE_FILE = "cache_file"
        private const val PERSISTENT_FILE = "persistent_file"

        private const val EXTERNAL_CACHE_FILE = "external_cache_file"
        private const val EXTERNAL_PERSISTENT_FILE = "external_persistent_file"
    }

    private val persistentFilesDir: File
        get() = requireContext().filesDir

    private val cacheFilesDir: File
        get() = requireContext().cacheDir

    private val externalPersistentFilesDir: File?
        get() = requireContext().getExternalFilesDir(null)

    private val externalCacheFilesDir: File?
        get() = requireContext().externalCacheDir

    private var inputText: String
        get() = binding.etInput.text.toString()
        set(value) {
            binding.tvSavedValue.text = value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCache.setOnClickListener { writeToCacheFile(inputText) }
        binding.btnPersistent.setOnClickListener { writeToPersistentFileUsingStream(inputText) }

        binding.btnReadCache.setOnClickListener { readFromCache() }
        binding.btnReadPersistent.setOnClickListener { readFromPersistentFile() }

        binding.btnExternalCache.setOnClickListener { writeToExternalCache(inputText) }
        binding.btnExternalPersistent.setOnClickListener { writeToExternalPersistent(inputText) }

        binding.btnReadExternalCache.setOnClickListener { readFromExternalCache() }
        binding.btnReadExternalPersistent.setOnClickListener { readFromExternalPersistent() }
    }

    private fun writeToCacheFile(text: String) {
        val cacheFile = File(cacheFilesDir, CACHE_FILE)
        cacheFile.writeBytes(text.toByteArray())
    }

    private fun writeToPersistentFileUsingFile(text: String) {
        val persistentFile = File(persistentFilesDir, PERSISTENT_FILE)
        persistentFile.writeBytes(text.toByteArray())
    }

    private fun writeToPersistentFileUsingStream(text: String) {
        requireContext().openFileOutput(PERSISTENT_FILE, Context.MODE_PRIVATE).use {
            it.write(text.toByteArray())
        }
    }

    private fun readFromCache() {
        val cacheFile = File(cacheFilesDir, CACHE_FILE)
        if (cacheFile.exists()) {
            val value = String(cacheFile.readBytes())
            inputText = value
        }
    }

    private fun readFromPersistentFile() {
        requireContext().openFileInput(PERSISTENT_FILE).bufferedReader().useLines { lines ->
            val value = lines.fold("") { acc, text -> if (acc.isBlank()) text else "$acc\n$text" }
            inputText = value
        }
    }

    private fun writeToExternalCache(text: String) {
        if (isExternalStorageWriteable()) {
            val externalCacheFile =
                externalCacheFilesDir?.let { File(it, EXTERNAL_CACHE_FILE) } ?: return
            externalCacheFile.writeBytes(text.toByteArray())
        }
    }

    private fun writeToExternalPersistent(text: String) {
        if (isExternalStorageWriteable()) {
            val externalPersistentFile =
                externalPersistentFilesDir?.let { File(it, EXTERNAL_PERSISTENT_FILE) } ?: return
            externalPersistentFile.writeBytes(text.toByteArray())
        }
    }

    private fun readFromExternalCache() {
        if (isExternalStorageReadable()) {
            val externalCacheFile =
                externalCacheFilesDir?.let { File(it, EXTERNAL_CACHE_FILE) } ?: return
            if (externalCacheFile.exists()) {
                inputText = String(externalCacheFile.readBytes())
            }
        }
    }

    private fun readFromExternalPersistent() {
        if (isExternalStorageReadable()) {
            val externalPersistentFile =
                externalPersistentFilesDir?.let { File(it, EXTERNAL_PERSISTENT_FILE) } ?: return
            if (externalPersistentFile.exists()) {
                inputText = String(externalPersistentFile.readBytes())
            }
        }
    }

    private fun isExternalStorageWriteable(): Boolean =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    private fun isExternalStorageReadable(): Boolean =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
                Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY

}