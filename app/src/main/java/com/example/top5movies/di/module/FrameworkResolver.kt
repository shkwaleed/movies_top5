

package com.example.top5movies.di.module

import android.content.Context
import com.example.top5movies.data.local.datastore.UIModeDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/*
* The class FrameworkResolver
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to resolve all the hard android framework dependent objects
*/
@Module
@InstallIn(SingletonComponent::class)
object FrameworkResolver {

    @Singleton
    @Provides
    fun providesUIModelDataStore(@ApplicationContext context: Context): UIModeDataStore {
        return UIModeDataStore(context)
    }


}
