package com.example.top5movies.di.module

import com.example.top5movies.data.local.datastore.UIModeDataStore
import com.example.top5movies.data.local.datastore.UIModeMutableStore
import com.example.top5movies.data.local.datastore.UIModeReadStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



/*
* The class DomainResolver
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to transforms hard android framework dependencies to android free logic objects
*/
@Module
@InstallIn(SingletonComponent::class)
abstract class DomainResolver {

    @Binds
    @Singleton
    abstract fun bindUIModeMutableStore(uiModeDataStore: UIModeDataStore): UIModeMutableStore

    @Binds
    @Singleton
    abstract fun bindUIModeReadStore(uiModeDataStore: UIModeDataStore): UIModeReadStore
}
