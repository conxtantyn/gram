package com.gram.app

import com.gram.gallery.provider.PreferenceProvider
import com.gram.gallery.provider.RepositoryProvider

interface GramComponent : PreferenceProvider, RepositoryProvider
