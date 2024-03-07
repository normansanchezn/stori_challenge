package com.example.storichallenge.modules.home.home.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.storichallenge.base.BaseViewModel
import com.example.storichallenge.extensions.safeSetValue
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.AccountBalance
import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.account.domain.useCases.GetLocalAccountUseCase
import com.example.storichallenge.modules.home.data.model.TransactionItem
import com.example.storichallenge.modules.home.home.domain.useCases.GetTotalBalanceUseCase
import com.example.storichallenge.modules.home.home.domain.useCases.GetTransactionHistoryUseCase
import com.example.storichallenge.utils.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocalAccountUseCase: GetLocalAccountUseCase,
    private val getTotalBalanceUseCase: GetTotalBalanceUseCase,
    private val getTransactionHistoryUseCase: GetTransactionHistoryUseCase
): BaseViewModel() {

    private var localAccount: Account ?= null
    private val onGetTotalBalance: SingleLiveData<AccountBalance> = SingleLiveData()
    private val onGetTransactionHistory: SingleLiveData<List<TransactionItem>> = SingleLiveData()

    fun getTotalBalance() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            localAccount = localAccountDeferred.await()
            getTotalBalanceUseCase.invoke(email = localAccount?.email!!)
                .collect { result ->
                    when(result) {
                        is ResultFirebase.Error -> {
                            onShowErrorMLD.safeSetValue(result.message)
                        }
                        is ResultFirebase.Success -> {
                            onGetTotalBalance.safeSetValue(result.data)
                        }
                    }
                }
        }
    }

    fun getTransactionHistory() {
        viewModelScope.launch {
            val localAccountDeferred = async { getLocalAccountUseCase.invoke() }
            localAccount = localAccountDeferred.await()
            getTransactionHistoryUseCase.invoke(localAccount?.email!!)
                .collect { result ->
                    when(result) {
                        is ResultFirebase.Error -> {
                            onShowErrorMLD.safeSetValue(result.message)
                        }
                        is ResultFirebase.Success -> {
                            onGetTransactionHistory.safeSetValue(result.data)
                        }
                    }
                }
        }
    }

    fun onGetTotalBalance(): LiveData<AccountBalance> = onGetTotalBalance
    fun onGetTransactionHistory(): LiveData<List<TransactionItem>> = onGetTransactionHistory
}