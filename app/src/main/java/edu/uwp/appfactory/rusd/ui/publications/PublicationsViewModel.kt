package edu.uwp.appfactory.rusd.ui.publications

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.BindingAdapter
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import edu.uwp.appfactory.rusd.data.model.Publication
import edu.uwp.appfactory.rusd.data.local.PublicationsRepository

/**
 * Created by dakota on 6/30/17.
 * Edited by jeremiah on 7/21/17.
 */

/**
 * Do NOT reference Views, Fragments, or Activities in this class
 */
class PublicationsViewModel(publicationsRepository: PublicationsRepository) : ViewModel() {

    private var mPublications: List<Publication>? = publicationsRepository.getAllPublications()

    fun getPublications() : List<Publication>? {
        return mPublications
    }

    companion object {

        @JvmStatic
        @BindingAdapter("imageFile")
        fun setImageFile(imageView: ImageView, fileName: String) {
            val context = imageView.context
            imageView.setImageResource(context.resources.getIdentifier(
                    fileName.replace(".png", ""), "drawable", context.packageName))
        }

        @JvmStatic
        @BindingAdapter("onImageClick")
        fun onImageClick(imageView: ImageView, URL: String) {
            imageView.setOnClickListener {
                (imageView.context as AppCompatActivity).startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(URL)))
            }
        }
    }
}