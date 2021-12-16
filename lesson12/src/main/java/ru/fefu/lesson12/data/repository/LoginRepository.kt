package ru.fefu.lesson12.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Path
import ru.fefu.lesson12.data.NetworkService
import ru.fefu.lesson12.data.Result
import ru.fefu.lesson12.data.api.LoginApi
import ru.fefu.lesson12.data.asFlow
import ru.fefu.lesson12.data.await
import ru.fefu.lesson12.domain.entities.RegisterEntity

class LoginRepository(private val service: NetworkService) {

    private val loginApi = service.retrofit.create(LoginApi::class.java)

    suspend fun register(
        login: String,
        password: String,
        name: String,
        gender: Int
    ): Flow<Result<RegisterEntity>> =
        flow<Result<RegisterEntity>> {
            emit(
                Result.Success(
                    loginApi.register(
                        login,
                        password,
                        name,
                        gender
                    )
                )
            )
        }
            .catch { emit(Result.Error(it)) }
            .flowOn(Dispatchers.IO)

    suspend fun login(
        login: String,
        password: String
    ): Flow<Result<RegisterEntity>> =
        flow<Result<RegisterEntity>> {
            emit(
                Result.Success(
                    loginApi.login(login, password)
                )
            )
        }
            .catch { emit(Result.Error(it)) }
            .flowOn(Dispatchers.IO)

}