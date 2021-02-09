package com.github.presentation.model

class Pagination(var totalCount: Int = 0,
                 var resultPerPage: Int = 10,
                 var currentPageNumber: Int = 1,
                 var isLoading: Boolean = false){

    fun totalPageCount(): Int {
        return totalCount / resultPerPage
    }


    fun isLastPage(): Boolean {
        return currentPageNumber == totalPageCount()
    }

}