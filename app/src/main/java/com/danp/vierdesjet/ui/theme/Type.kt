package com.danp.vierdesjet.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.danp.vierdesjet.R

// Set of Material typography styles to start with
val PoppinsFont = FontFamily(
    Font(R.font.poppins_black,FontWeight.Normal)
)
val chunkfive = FontFamily(
    Font(R.font.chunk_five_print,FontWeight.Normal)
)
val antonio = FontFamily(
    Font(R.font.antonio_bold,FontWeight.Normal)
)
val burnstown= FontFamily(
    Font(R.font.burnstown_dam,FontWeight.Normal)
)
val underwood_champion= FontFamily(
    Font(R.font.underwood_champion,FontWeight.Normal)
)
val woodenni= FontFamily(
    Font(R.font.woodenni,FontWeight.Normal)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = antonio,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = woodenni,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),


    button = TextStyle(
        fontFamily = underwood_champion ,
        fontWeight = FontWeight.W500,

        fontSize = 17.sp
    ),
    caption = TextStyle(
        fontFamily = chunkfive,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)