package com.example.f1game.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.f1game.R


val MAX_NO_OF_WORDS = 10
val POINTS_FOR_ANSWER = 20
val POINTS_WITH_HINT =10

data class drivers(
    @DrawableRes val imageId : Int,
     val nameDrivers : String,
    val hintId : Int
)

val listOfDrivers : List<drivers> = listOf(
    drivers(R.drawable.lewis_hamilton_mercedes,"lewis hamilton",R.string.hamilton1),
    drivers(R.drawable.max_verstappen_red_bull_racing,"max verstrappen",R.string.max1),
    drivers(R.drawable.alex_albon_williams,"alex albon",R.string.albon1),
    drivers(R.drawable.carlos_sainz_ferrari,"carlos sainz",R.string.sainz1),
    drivers(R.drawable.charles_leclerc_ferrari,"charles leclerc",R.string.leclerc1),
    drivers(R.drawable.daniel_ricciardo_racing_bulls,"daniel ricciardo",R.string.ricciardo1),
    drivers(R.drawable.esteban_ocon_alpine,"esteban ocon",R.string.ocon1),
    drivers(R.drawable.f1_abu_dhabi_gp_2012_felipe_massa_ferrari,"felipe massa",R.string.massa1),
    drivers(R.drawable.f1_australian_gp_2016_nico_rosberg_mercedes_amg_f1_team,"nico rosberg",R.string.nico1),
    drivers(R.drawable.fernando_alonso_aston_martin_r,"fernando alonso",R.string.alonso1),
    drivers(R.drawable.george_russell_mercedes,"george russell",R.string.russell1),
    drivers(R.drawable.kevin_magnussen_haas_f1_team,"kevin magnussen",R.string.magnussen1),
    drivers(R.drawable.kimi_raikkonen_alfa_romeo_raci_1,"kimi raikkonen",R.string.kimi1),
    drivers(R.drawable.lance_stroll_aston_martin,"lance stroll",R.string.stroll1),
    drivers(R.drawable.lando_norris_mclaren,"lando norris",R.string.norris1),
    drivers(R.drawable.logan_sargeant_williams,"logan sargeant",R.string.sargeant1),
    drivers(R.drawable.michael_schumacher_mercedes_a_1,"michael schumacher",R.string.schumacher1),
    drivers(R.drawable.nicholas_latifi_williams_racin_1,"nicholas latifi",R.string.latifi1),
    drivers(R.drawable.nico_hulkenberg_haas_f1_team,"nico hulkenberg",R.string.hulkenberg1),
    drivers(R.drawable.oliver_bearman_ferrari,"oliver bearman",R.string.bearman1),
    drivers(R.drawable.oscar_piastri_mclaren,"oscar piastri",R.string.piastri1),
    drivers(R.drawable.pierre_gasly_alpine,"pierre gasly",R.string.gasly1),
    drivers(R.drawable.sebastian_vettel_aston_martin__1,"sebastian vettel",R.string.vettel1),
    drivers(R.drawable.sergio_perez_red_bull_racing,"sergio perez",R.string.checo1),
    drivers(R.drawable.valtteri_bottas_stake_f1_team_,"valteri bottas",R.string.bottas1),
    drivers(R.drawable.yuki_tsunoda_racing_bulls,"yuki tsunoda",R.string.yuki1),
    drivers(R.drawable.zhou_guanyu_stake_f1_team_kick,"zhou guanyu",R.string.zhou1)
)