package com.danp.vierdesjet.paging

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.danp.vierdesjet.room.plant.PlantEntity
import kotlinx.coroutines.flow.Flow

class PlantViewModel(context: Context, group: String) : ViewModel() {
    private val backendService: PlantBackendServices = PlantBackendServices(context,group,200,5)
    val plants: Flow<PagingData<PlantEntity>> = Pager(
        PagingConfig(pageSize = 5,
        enablePlaceholders = false,
        prefetchDistance = 3)
    ) {
        PlantPagingSource(backendService)
    }.flow
}