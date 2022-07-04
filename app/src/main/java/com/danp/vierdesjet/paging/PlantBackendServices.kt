package com.danp.vierdesjet.paging

import android.content.Context
import androidx.compose.runtime.collectAsState
import com.danp.vierdesjet.DataStoreManager
import com.danp.vierdesjet.room.plant.PlantApp
import com.danp.vierdesjet.room.plant.PlantEntity

class PlantBackendServices(context: Context, group: String, private val totalCount:Int, private val pageSize:Int) {
    private val plantRoom = PlantApp(context).room
    private val group = group

    suspend fun searchPlants(nextPageNumber: Int): List<PlantEntity> {
        val startIndex = (nextPageNumber - 1) * pageSize + 1
        var endIndex = startIndex + pageSize
        if (endIndex > totalCount) {
            endIndex = totalCount
        }
        return (startIndex..endIndex).map { index -> plantRoom.plantDao().getByGroup(index,group)}
    }
}