package com.f.financeapp

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

// поток ввода-вывода используемый для заполнения БД при первом запуске приложения
fun ioThread(f : () -> Unit) {
    IO_EXECUTOR.execute(f)
}