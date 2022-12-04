//package com.example.smb_p01
//
//import android.content.*
//import android.database.Cursor
//import android.net.Uri
//import java.util.concurrent.Callable
//
//
//class ProductContentProvider : ContentProvider() {
//    companion object {
//        const val AUTHORITY = "com.example.smb_p01.ProductContentProvider"
//        private const val CODE_PRODUCT_DIR = 1
//        private const val CODE_PRODUCT_ITEM = 2
//        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)
//
//        init {
//            MATCHER.addURI(
//                AUTHORITY,
//                "product",
//                CODE_PRODUCT_DIR
//            )
//            MATCHER.addURI(
//                AUTHORITY,
//                "product/*",
//                CODE_PRODUCT_ITEM
//            )
//        }
//    }
//
//    override fun onCreate(): Boolean {
//        return true
//    }
//
//    override fun update(
//        uri: Uri, values: ContentValues?, selection: String?,
//        selectionArgs: Array<String?>?
//    ): Int {
//        return when (MATCHER.match(uri)) {
//            CODE_PRODUCT_DIR -> throw java.lang.IllegalArgumentException("Invalid URI, cannot update without ID$uri")
//            CODE_PRODUCT_ITEM -> {
//                val context = context ?: return 0
//                val product = Product(
//                    id = values?.get("id") as String,
//                    name = values.get("name") as String,
//                    price = values.get("price") as Double,
//                    amount = values.get("amount") as Double,
//                    isBought = values.get("isBought") as Boolean,
//                )
//                val count: Int = ProductDB.getDB(context).product.update(product)
//                context.contentResolver.notifyChange(uri, null)
//                count
//            }
//            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
//        }
//    }
//
//    override fun delete(
//        uri: Uri, selection: String?,
//        selectionArgs: Array<String?>?
//    ): Int {
//        return when (MATCHER.match(uri)) {
//            CODE_PRODUCT_ITEM -> {
//                val context = context ?: return 0
//                val count: Int = ProductDB.getDB(context).product.delete(ContentUris.parseId(uri))
//                context.contentResolver.notifyChange(uri, null)
//                count
//            }
//            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
//        }
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        return when (MATCHER.match(uri)) {
//            CODE_PRODUCT_DIR -> {
//                val context = context ?: return null
////                TODO: how to do it in simpler way
//                val product = Product(
//                    name = values!!.get("name") as String,
//                    price = values.get("price") as Double,
//                    amount = values.get("amount") as Double,
//                    isBought = values.get("isBought") as Boolean,
//                )
//                val id: Long = ProductDB.getDB(context).product.insert(product)
//                context.contentResolver.notifyChange(uri, null)
//                ContentUris.withAppendedId(uri, id)
//            }
//            CODE_PRODUCT_ITEM -> throw java.lang.IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
//            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
//        }
//    }
//
//    override fun query(
//        uri: Uri, projection: Array<String>?, selection: String?,
//        selectionArgs: Array<String>?, sortOrder: String?
//    ): Cursor? {
//        val code = MATCHER.match(uri)
//        return if (code == CODE_PRODUCT_DIR || code == CODE_PRODUCT_ITEM) {
//            val context = context ?: return null
//            val productDao: ProductDao = ProductDB.getDB(context).product
//            val cursor: Cursor = if (code == CODE_PRODUCT_DIR) {
//                productDao.getProductsCursor()
//            } else {
//                productDao.getProductById(ContentUris.parseId(uri))
//            }
//            cursor.setNotificationUri(context.contentResolver, uri)
//            cursor
//        } else {
//            throw IllegalArgumentException("Unknown URI: $uri")
//        }
//    }
//
//    override fun getType(uri: Uri): String? {
//        return when (MATCHER.match(uri)) {
//            CODE_PRODUCT_DIR -> "vnd.android.cursor.dir/$AUTHORITY.companyTM"
//            CODE_PRODUCT_ITEM -> "vnd.android.cursor.item/$AUTHORITY.companyTM"
//            else -> throw IllegalArgumentException("Unknown URI: $uri")
//        }
//    }
//
//
//    @Throws(OperationApplicationException::class)
//    override fun applyBatch(
//        operations: ArrayList<ContentProviderOperation?>
//    ): Array<ContentProviderResult?> {
//        val context = context ?: return arrayOfNulls(0)
//        val database: ProductDB = ProductDB.getDB(context)
//        return database.runInTransaction(Callable { super.applyBatch(operations) })
//    }
//
//
//}