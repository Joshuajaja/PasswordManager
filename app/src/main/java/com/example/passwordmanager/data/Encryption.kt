package com.example.passwordmanager.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

data class EncryptionData(
    val iv: String,
    val ciphertext: String
)

object KeyManager {

    private const val KEY_ALIAS = "my_password_manager_key"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"

    fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        // 1. Try to get existing key
        val existingKey = keyStore.getKey(KEY_ALIAS, null)
        if (existingKey is SecretKey) {
            return existingKey
        }

        // 2. Create new key if it doesn't exist
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )

        val keySpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        keyGenerator.init(keySpec)
        return keyGenerator.generateKey()
    }
}
fun cipherString(inputString: String): EncryptionData {
    val key = KeyManager.getKey()
    val inputByteArray: ByteArray = inputString.toByteArray()
    val cipher = Cipher.getInstance("AES/GCM/NoPadding") // encryption algorithm, mode, padding
    cipher.init(Cipher.ENCRYPT_MODE, key) // cipher init
    val ciphertext: ByteArray = cipher.doFinal(inputByteArray) //encrypted message
    val iv: ByteArray = cipher.iv // init vector

    return EncryptionData(
        iv = Base64.encodeToString(iv, Base64.DEFAULT),
        ciphertext = Base64.encodeToString(ciphertext, Base64.DEFAULT)
    )
}

fun cipherInt(inputInt: Int): EncryptionData {
    val key = KeyManager.getKey()
    val inputByteArray: ByteArray = inputInt.toString().toByteArray()
    val cipher = Cipher.getInstance("AES/GCM/NoPadding") // encryption algorithm, mode, padding
    cipher.init(Cipher.ENCRYPT_MODE, key) // cipher init
    val ciphertext: ByteArray = cipher.doFinal(inputByteArray) //encrypted message
    val iv: ByteArray = cipher.iv // init vector

    return EncryptionData(
        iv = Base64.encodeToString(iv, Base64.DEFAULT),
        ciphertext = Base64.encodeToString(ciphertext, Base64.DEFAULT)
    )
}

fun decryptString(data: EncryptionData): String {
    val key = KeyManager.getKey()

    val iv = Base64.decode(data.iv, Base64.DEFAULT)
    val ciphertext = Base64.decode(data.ciphertext, Base64.DEFAULT)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")

    val spec = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, key, spec)

    val result = cipher.doFinal(ciphertext)
    return String(result, Charsets.UTF_8)
}

fun decryptInt(data: EncryptionData): Int {
    val key = KeyManager.getKey()

    val iv = Base64.decode(data.iv, Base64.DEFAULT)
    val ciphertext = Base64.decode(data.ciphertext, Base64.DEFAULT)

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")

    val spec = GCMParameterSpec(128, iv)
    cipher.init(Cipher.DECRYPT_MODE, key, spec)

    val result = cipher.doFinal(ciphertext)
    return String(result, Charsets.UTF_8).toInt()
}

fun hashString(inputString: String): ByteArray {
    val pin = inputString.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest: ByteArray = md.digest(pin)
    return digest
}