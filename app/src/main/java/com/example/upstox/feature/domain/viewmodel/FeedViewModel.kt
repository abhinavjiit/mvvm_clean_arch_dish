package com.example.upstox.feature.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upstox.base.IResult
import com.example.upstox.feature.data.model.FeedMealsListModel
import com.example.upstox.feature.domain.usecase.GetItemListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedDataUseCase: GetItemListUseCase) :
    ViewModel() {

    private var _feedData = MutableLiveData<IResult<FeedMealsListModel>>()
    val feedData: LiveData<IResult<FeedMealsListModel>> = _feedData


    fun fetchFeedList(endPoint: String) {
        viewModelScope.launch {
            feedDataUseCase(endPoint).catch {
                _feedData.postValue(IResult.Error(it))
            }.collect {
                _feedData.postValue(it)
            }
        }
    }


}