package com.doinglab.wonjong

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author leewonjong@doinglab.com
 */
class MainViewModel : ViewModel() {
    private val _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    private val _file = MutableLiveData("this is empty")
    val file: LiveData<String> = _file

    fun onButtonClick(type: ButtonClickType) {
        when (type) {
            ButtonClickType.PLUS -> {
                val afterCount = getPlusCount(count.value)
                _count.postValue(afterCount)
                _file.postValue(plusFile(_file.value)(afterCount))
            }
            ButtonClickType.MINUS -> {
                minusCount(count.value)
                _file.postValue(minusFile(_file.value, minusFunc))
            }
            ButtonClickType.CLEAR -> {
                _count.postValue(0)
            }
        }
    }

    // 순수함수
    private fun getPlusCount(number: Int?): Int? {
        return number?.plus(1)
    }

    // 순수함수X
    private fun minusCount(number: Int?): Int? {
        _count.postValue(number?.minus(1)) // 외부값에 영향을 미치므로 순수함수 X
        return number?.minus(1)
    }

    // 커링
    private val plusFile: (String?) -> (Int?) -> String? = { str ->
        { num ->
            str.plus(num)
        }
    }

    // 함수 자체를 변수로 가질 수 있음
    private val minusFunc: (String?) -> String? = {
        if (it.isNullOrEmpty()) {
            null
        } else {
            it.substring(0, it.length - 1)
        }
    }

    // 일급함수 - 함수를 객체와 같이 파라미터로 받을 수 있음
    private fun minusFile(str: String?, minusFunc: ((String?) -> String?)): String? {
        return minusFunc.invoke(str)
    }

    @Suppress("UNCHECKED_CAST")
    class ViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel() as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}

enum class ButtonClickType {
    PLUS, MINUS, CLEAR
}