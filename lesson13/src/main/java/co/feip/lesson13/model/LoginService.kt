package co.feip.lesson13.model

import co.feip.lesson13.model.api.LoginApi
import co.feip.lesson13.model.dto.RegisterDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginService {

    private val loginApi = NetworkService().retrofit.create(LoginApi::class.java)

    suspend fun register(
        login: String,
        password: String,
        name: String,
        gender: Int
    ): Flow<Result<RegisterDto>> =
        flow<Result<RegisterDto>> {
            emit(Result.Success(loginApi.register(login, password, name, gender)))
        }.catch { emit(Result.Error(it)) }.flowOn(Dispatchers.IO)

    suspend fun login(
        login: String,
        password: String
    ): Flow<Result<RegisterDto>> =
        flow<Result<RegisterDto>> {
            emit(Result.Success(loginApi.login(login, password)))
        }.catch { emit(Result.Error(it)) }.flowOn(Dispatchers.IO)
}