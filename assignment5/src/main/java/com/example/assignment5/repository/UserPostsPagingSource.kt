package com.example.assignment5.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignment5.di.UserPost
import com.example.assignment5.model.UserPostsResponse
import com.example.assignment5.network.ProfileApiService
import com.example.assignment5.network.UserPostApiService
import javax.inject.Named

const val FIRST_PAGE = 1
class UserPostsPagingSource(@UserPost private val apiService: UserPostApiService) : PagingSource<Int, UserPostsResponse>() {
    override fun getRefreshKey(state: PagingState<Int, UserPostsResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPostsResponse> {
        return try {
            val page = params.key ?: FIRST_PAGE

            val response = apiService.getUserPosts(
                page = page,
                pageSize = params.loadSize
            )

            LoadResult.Page(
                data = response.body()!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.body()!!.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}