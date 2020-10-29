package de.smartsquare.kortance

import de.smartsquare.kortance.scenarios.soak.SoakCommand
import de.smartsquare.kortance.scenarios.spike.SpikeCommand
import de.smartsquare.kortance.scenarios.stress.StressCommand
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "kortance",
    version = ["0.7.0"],
    subcommands = [StressCommand::class, SpikeCommand::class, SoakCommand::class],
    mixinStandardHelpOptions = true
)
class Kortance : Callable<Int> {

    @CommandLine.Spec
    var spec: CommandLine.Model.CommandSpec? = null

    override fun call(): Int {
        throw CommandLine.ParameterException(spec?.commandLine(), "Please specify the scenario that fits your needs!")
    }
}

fun main(args: Array<String>) {
    CommandLine(Kortance()).execute(*args)
}
