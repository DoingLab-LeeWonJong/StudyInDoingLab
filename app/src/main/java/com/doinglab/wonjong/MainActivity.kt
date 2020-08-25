package com.doinglab.wonjong

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.doinglab.wonjong.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.ViewModelFactory()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
            model = viewModel
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.count.observe(this) {
            Snackbar.make(root, it.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }
}