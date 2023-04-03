package com.example.assignment4_pagination.repositoty

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignment4_pagination.model.UserRepository
import com.example.assignment4_pagination.network.GithubApiService

const val FIRST_PAGE = 1

class RepositoryPagingDataSource(
    private val apiService: GithubApiService,
    private val searchQuery: String
) : PagingSource<Int, UserRepository>() {
    override fun getRefreshKey(state: PagingState<Int, UserRepository>): Int? {
         return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepository> {
        return try {
            val page = params.key ?: FIRST_PAGE

            val response = apiService.getGitHubRepositories(
                username = searchQuery,
                page = page,
                size = params.loadSize
            )

            LoadResult.Page(
                data = response.repositories,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.repositories.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}