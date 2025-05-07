package com.juan.domain.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDateTime.formatToString() = format(
    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
)