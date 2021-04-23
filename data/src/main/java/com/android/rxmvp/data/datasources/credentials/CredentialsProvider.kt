package com.android.rxmvp.data.datasources.credentials

interface CredentialsProvider {

    val clientId: String

    val baseUrl: String
}