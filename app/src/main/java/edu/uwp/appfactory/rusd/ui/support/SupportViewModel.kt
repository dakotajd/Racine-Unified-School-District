package edu.uwp.appfactory.rusd.ui.support

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.BindingAdapter
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.View
import edu.uwp.appfactory.rusd.data.local.ContactRepository
import edu.uwp.appfactory.rusd.data.local.PublicationsRepository
import edu.uwp.appfactory.rusd.data.model.Contact
import edu.uwp.appfactory.rusd.data.model.Publication

/**
 * Created by dakota on 7/4/17.
 *
 * Edited by Jeremiah on 7/21/2017.
 */

class SupportViewModel(contactRepository: ContactRepository, publicationsRepository: PublicationsRepository): ViewModel() {

    private var contacts: LiveData<List<Contact>> = contactRepository.getAllContacts()
    private var publications: List<Publication>? = publicationsRepository.getSupportPublications()

    fun getContacts() : LiveData<List<Contact>> {
        return contacts
    }

    fun getSupportPublications(): List<Publication>? {
        return publications
    }

    companion object {

        @JvmStatic
        @BindingAdapter("setVisibility")
        fun setVisibility(v: View, phone: String?) {
            if (phone != null) {
                v.visibility = View.VISIBLE
            } else {
                v.visibility = View.INVISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("onCallClick")
        fun onCallClick(v: View, phone: String?) {
            v.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + phone)
                ContextCompat.startActivity(v.context, intent, null)
            }
        }

        @JvmStatic
        @BindingAdapter("onMessageClick")
        fun onMessageClick(v: View, email: String) {
            v.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:" + email)
                ContextCompat.startActivity(v.context, Intent.createChooser(intent, "Send email"), null)
            }
        }
    }
}