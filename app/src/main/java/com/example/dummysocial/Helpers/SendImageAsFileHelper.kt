package com.example.dummysocial.Helpers

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.concurrent.thread


fun sendImageAsFile(context: Context, imgUrl: String) {
    try {
        thread {
            var url: URL? = null
            try {
                // get url from EditText view and converting
                // it to the URL
                url = URL(imgUrl.toString())
            } catch (e: MalformedURLException) {
                // will invoke if invalid url is entered
                e.printStackTrace()
            }
            var connection: HttpURLConnection? = null
            try {
                assert(url != null)
                connection = url!!.openConnection() as HttpURLConnection
            } catch (e: IOException) {
                e.printStackTrace()
            }
            assert(connection != null)
            connection!!.doInput = true
            try {
                connection.connect()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var input: InputStream? = null
            try {
                input = connection.inputStream
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // generation of image form url Input
            val imgBitmap = BitmapFactory.decodeStream(input)
            val rand = Random()
            val randNo = rand.nextInt(100000)
            val imgBitmapPath = MediaStore.Images.Media.insertImage(
                context.contentResolver, imgBitmap,
                "IMG:$randNo", null
            )
            val imgBitmapUri = Uri.parse(imgBitmapPath)

            Log.d("dataxx", "sendImageAsFilePath: ${imgBitmapUri.getPath()}")


            // share Intent
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri)
            shareIntent.type = "image/png"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Testing")
            // Open the chooser dialog box
            context.startActivity(Intent.createChooser(shareIntent, "Share with"))

//            val file: File = File(imgBitmapPath)
//            file.deleteOnExit()

//            val fdelete: File = File(imgBitmapUri.path)
//            if (fdelete.exists()) {
//                if (fdelete.delete()) {
//                    Log.d("dataxx", "deleted: ${imgBitmapUri.path}")
//                } else {
//                    Log.d("dataxx", "not deleted: ${imgBitmapUri.path}")
//                }
//            }

//            val contentResolver: ContentResolver = context.contentResolver
//            contentResolver.delete(imgBitmapUri, null, null)


        }.start()
    } catch (e: Exception) {
        Log.d("dataxx", "ERROR: ${e.message}")
    }


//    Picasso.get().load(imgUrl).into(object : com.squareup.picasso.Target {
//        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//            Log.d("dataxx", "onBitmapLoaded: ")
////            val intent = Intent(Intent.ACTION_SEND)
////            intent.type = "image/*"
////            intent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap, context))
////            context.startActivity(Intent.createChooser(intent, "Share Image"))
//
//            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            shareIntent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap, context))
//            shareIntent.type = "image/*"
//            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "Testing")
//            // Open the chooser dialog box
//            context.startActivity(Intent.createChooser(shareIntent, "Share with"))
//        }
//
//        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//            Log.d("dataxx", "onPrepareLoad: ")
//        }
//
//        override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
//            Log.d("dataxx", "onBitmapFailed: ")
//        }
//    })


}

fun getBitmapFromView(bmp: Bitmap?, context: Context): Uri? {
    var bmpUri: Uri? = null
    try {
        val file = File(context.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")

        val out = FileOutputStream(file)
        bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.close()
        bmpUri = Uri.fromFile(file)

    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("dataxx", "getBitmapFromView: ${e.message}")
    }
    return bmpUri
}