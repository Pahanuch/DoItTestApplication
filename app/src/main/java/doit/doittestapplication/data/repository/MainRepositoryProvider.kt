package doit.doittestapplication.data.repository

object MainRepositoryProvider {
    val instance: MainRepository by lazy { MainRepositoryImpl() }
}