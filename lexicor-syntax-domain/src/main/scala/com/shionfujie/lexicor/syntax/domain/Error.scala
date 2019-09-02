package com.shionfujie.lexicor.syntax.domain

import com.shionfujie.lexicor.core.domain.Pos

case class Error(at: Pos, message: String)
