package com.example.finalassignment.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.finalassignment.model.ProfileResponse
import com.example.finalassignment.network.ProfileApiService
import com.example.finalassignment.network.UserPostApiService

const val FIRST_PAGE = 1

class UserProfilePagingSource(private val apiService: ProfileApiService) : PagingSource<Int, ProfileResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ProfileResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProfileResponse> {
        return try {
            val page = params.key ?: FIRST_PAGE

            val response = apiService.getUserProfileList(
                page = page,
                limit = params.loadSize
            )

            /*if(!response.isSuccessful) {
                LoadResult.Error(throw Exception("Network request is failed."))
            }*/
            LoadResult.Page(
                data = response?.body() ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.body()!!.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}