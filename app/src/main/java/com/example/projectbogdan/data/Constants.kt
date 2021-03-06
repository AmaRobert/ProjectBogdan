package com.example.projectbogdan.data

import androidx.annotation.IntDef

object Constants {

    const val PRODUCT_CATEGORY_NONE = 0
    const val PRODUCT_CATEGORY_PASTA_AND_RICE = 1
    const val PRODUCT_CATEGORY_BREAD_ADN_PASTRIES = 2
    const val PRODUCT_CATEGORY_VEGETABLES_AND_FRUITS = 3
    const val PRODUCT_CATEGORY_DIARY_AND_EGGS = 4
    const val PRODUCT_CATEGORY_MEAT_AND_FISH = 5
    const val PRODUCT_CATEGORY_SWEATS_AND_SNACKS = 6
    const val PRODUCT_CATEGORY_WATER_AND_BEVERAGES= 7
    const val PRODUCT_CATEGORY_OTHERS = 8

    @IntDef(value = [
        PRODUCT_CATEGORY_NONE,
        PRODUCT_CATEGORY_PASTA_AND_RICE,
        PRODUCT_CATEGORY_BREAD_ADN_PASTRIES,
        PRODUCT_CATEGORY_VEGETABLES_AND_FRUITS,
        PRODUCT_CATEGORY_DIARY_AND_EGGS,
        PRODUCT_CATEGORY_MEAT_AND_FISH,
        PRODUCT_CATEGORY_SWEATS_AND_SNACKS,
        PRODUCT_CATEGORY_WATER_AND_BEVERAGES,
        PRODUCT_CATEGORY_OTHERS
    ])
    annotation class ProductCategory
}