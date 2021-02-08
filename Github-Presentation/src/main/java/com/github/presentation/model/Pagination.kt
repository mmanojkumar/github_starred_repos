package com.github.presentation.model

import com.github.data.DataConstants

class Pagination(var totalCount: Int = 0,
                 var resultPerPage: Int = DataConstants.RESULT_PER_PAGE,
                 var currentPageNumber: Int = 1,
                 var isLoading: Boolean = false){

    fun totalPageCount(): Int {
        return totalCount / resultPerPage
    }


    fun isLastPage(): Boolean {
        return currentPageNumber == totalPageCount()
    }

}