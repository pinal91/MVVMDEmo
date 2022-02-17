package com.demo.pinal.model

data class BannerResponse(
	val Message: String? = null,
	val Data: BannerData? = null,
	val ErrorCode: String? = null
)

data class BannerData(
	val mainBanner: List<BannerItem?>? = null,
	val promotionalBanner2: List<BannerItem?>? = null,
	val promotionalBanner: List<BannerItem?>? = null,
	val brandZoneBanner: List<BannerItem?>? = null,
	val recommended: Recommended? = null
)

data class BannerItem(
	val imageUrl: String? = null,
	val id: String? = null
)


data class Recommended(
	val productTagId: Int? = null,
	val name: String? = null
)



