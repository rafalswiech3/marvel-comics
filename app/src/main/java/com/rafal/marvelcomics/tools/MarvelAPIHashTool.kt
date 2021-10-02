package com.rafal.marvelcomics.tools

import com.rafal.marvelcomics.di.PRIVATE_KEY
import com.rafal.marvelcomics.di.PUBLIC_KEY
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class MarvelAPIHashTool {
    companion object {
        fun getTimestamp() = Date().time
        fun getHash(): String {
            val hashInput = "${getTimestamp()}$PRIVATE_KEY$PUBLIC_KEY"
            return String.format(
                "%032x",
                BigInteger(
                    1,
                    MessageDigest.getInstance("MD5")
                        .digest(hashInput.toByteArray(Charsets.UTF_8))
                )
            )
        }
    }
}