package com.demo.pinal.model

data class ProductResponse(
	val Message: String? = null,
	val Data: ProductData? = null,
	val ErrorCode: String? = null
)

data class Pagination(
	val totalPage: Int? = null,
	val page: Int? = null,
	val totalCount: Int? = null,
	val rowsPerPage: Int? = null
)

data class ProductsItemModel(
	val imgUrl: String? = null,
	val ratingEmoji: String? = null,
	val productId: Int? = null,
	val name: String? = null,
	val rank: Int? = null,
	val localCrossedPrice: Int? = null,
	val brand: String? = null,
	val localPrice: Int? = null
)

data class ProductData(
	val Pagination: Pagination? = null,
	val marketList: List<ProductsItemModel?>? = null
)

