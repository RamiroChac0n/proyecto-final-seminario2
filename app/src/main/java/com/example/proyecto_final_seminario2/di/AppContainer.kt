package com.example.proyecto_final_seminario2.di

import android.content.Context
import com.example.proyecto_final_seminario2.data.auth.repositories.LoginRepository
import com.example.proyecto_final_seminario2.data.auth.repositories.RegisterRepository
import com.example.proyecto_final_seminario2.data.auth.repositories.RoomLoginRepository
import com.example.proyecto_final_seminario2.data.auth.repositories.RoomRegisterRepository
import com.example.proyecto_final_seminario2.data.local.AppDatabase

class AppContainer(context: Context) {

    private val database: AppDatabase = AppDatabase.getDatabase(
        context = context.applicationContext
    )

    val loginRepository: LoginRepository = RoomLoginRepository(
        userDao = database.userDao()
    )

    val registerRepository: RegisterRepository = RoomRegisterRepository(
        userDao = database.userDao()
    )
}