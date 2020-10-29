package de.smartsquare.kortance

import picocli.CommandLine

class WaveOptions {

    @CommandLine.Option(names = ["-w", "--waves"])
    var waves: Int = 10

    @CommandLine.Option(names = ["-d", "--delay"])
    var waveDelay: Long = 30_000
}
