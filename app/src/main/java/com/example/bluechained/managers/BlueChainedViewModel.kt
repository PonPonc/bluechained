package com.example.bluechained.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bluechained.data.BlockEntity
import com.example.bluechained.data.BlockRepository
import com.example.bluechained.managers.BlueChainedUiState
import com.example.bluechained.managers.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BlueChainedViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferencesManager = SharedPreferencesManager(application.applicationContext)
    private val repository = BlockRepository(application.applicationContext)

    val allBlocks = repository.allBlocks

    private val _uiState = MutableStateFlow(
        BlueChainedUiState(
            encryptionStatus = sharedPreferencesManager.getEncryptionStatus(),
            proofOfWork = sharedPreferencesManager.getPowValue()
        )
    )
    val uiState: StateFlow<BlueChainedUiState> = _uiState.asStateFlow()

    private val _powValue = MutableLiveData<Int>().apply {
        value = sharedPreferencesManager.getPowValue()
    }
    val powValue: LiveData<Int> get() = _powValue

    private val _encryptionStatus = MutableLiveData<Boolean>().apply {
        value = sharedPreferencesManager.getEncryptionStatus()
    }
    val encryptionStatus: LiveData<Boolean> get() = _encryptionStatus

    fun setPowValue(value: Int) {
        sharedPreferencesManager.setPowValue(value)
        _powValue.value = value
        updateUiState()
    }

    fun setEncryptionStatus(status: Boolean) {
        sharedPreferencesManager.setEncryptionStatus(status)
        _encryptionStatus.value = status
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.value = BlueChainedUiState(
            encryptionStatus = _encryptionStatus.value ?: false,
            proofOfWork = _powValue.value ?: 2
        )
    }

    fun addNewBlock(message: String, encryptionStatus: Boolean, hashValue: String){
        val timestamp = System.currentTimeMillis()
        val newBlock = BlockEntity(
            message = message,
            encryptionStatus = encryptionStatus,
            hashValue = hashValue
        )
        viewModelScope.launch {
            repository.insert(newBlock)
        }
    }
}
