package com.maliha.i202606

data class Mentor(val name: String? = null, val desc: String? = null, val price: Int? = null,
                  val status: String? = null, val type: String? = null, var fav: Boolean = true,
                  val username : String = "", val rating: Double)
