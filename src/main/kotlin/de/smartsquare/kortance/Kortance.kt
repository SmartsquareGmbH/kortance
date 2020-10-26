package de.smartsquare.kortance

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import de.smartsquare.kortance.scenarios.soak.SoakCommand
import de.smartsquare.kortance.scenarios.spike.SpikeCommand
import de.smartsquare.kortance.scenarios.stress.StressCommand

class Kortance : CliktCommand() {

    override fun run() {}
}

fun main(args: Array<String>) = Kortance().subcommands(StressCommand(), SoakCommand(), SpikeCommand()).main(args)
