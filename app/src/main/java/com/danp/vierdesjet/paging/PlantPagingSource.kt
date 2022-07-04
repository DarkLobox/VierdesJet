package com.danp.vierdesjet.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.danp.vierdesjet.room.plant.PlantEntity
import java.io.IOException

class PlantPagingSource(
    private val backendService: PlantBackendServices
): PagingSource<Int, PlantEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlantEntity> {
        return try {
            val nextPageNumber = params.key ?: 1
            val plants = backendService.searchPlants(nextPageNumber)
            return LoadResult.Page(
                data = plants,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (plants.isEmpty()) null else nextPageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, PlantEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}