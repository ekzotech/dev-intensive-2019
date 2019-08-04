package ru.skillbranch.devintensive.viewmodels

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class ProfileViewModel : ViewModel() {
    private val repository : PreferencesRepository = PreferencesRepository
    private val profileData = MutableLiveData<Profile>()
    private val appTheme = MutableLiveData<Int>()
    private val isValidRepo = MutableLiveData<Boolean>()

    init {
        Log.d("M_ProfileViewModel", "init view model")
        profileData.value = repository.getProfile()
        appTheme.value = repository.getAppTheme()
        isValidRepo.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("M_ProfileViewModel", "view model cleared")
    }

    fun getProfileData() : LiveData<Profile> = profileData

    fun getTheme() :LiveData<Int> = appTheme

    fun isValidRepo(): LiveData<Boolean> = isValidRepo

    fun saveProfileData(profile: Profile) {
        repository.saveProfileData(profile)
        profileData.value = profile
    }

    fun repositoryValidation(repository: String) {
        isValidRepo.value = repository.isEmpty() || repository.matches(
            Regex("^(https://)?(www.)?(github.com/)(?!(${githubExcludedRepos()})(?=/|$))(?![\\W])(?!\\w+[-]{2})[a-zA-Z0-9-]+(?<![-])(/)?$"))
    }

    private fun githubExcludedRepos(): String {
        val repos = arrayOf(
            "enterprise", "features", "topics", "collections", "trending", "events", "join",
            "pricing", "nonprofit", "customer-stories", "security", "login", "marketplace"
        )
        return repos.joinToString("|")
    }

    fun switchTheme() {
        if(appTheme.value == AppCompatDelegate.MODE_NIGHT_YES) {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        } else {
            appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        }
        repository.saveAppTheme(appTheme.value!!)
    }
}