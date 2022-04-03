package com.example.notesapp

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
class GlideApp : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}