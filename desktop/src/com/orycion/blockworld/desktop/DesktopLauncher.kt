package com.orycion.blockworld.desktop

import kotlin.jvm.JvmStatic
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.orycion.blockworld.BlockWorldGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()
        Lwjgl3Application(BlockWorldGame(), config)
    }
}