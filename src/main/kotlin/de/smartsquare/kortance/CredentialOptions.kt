package de.smartsquare.kortance

import picocli.CommandLine

class CredentialOptions {

    @CommandLine.Option(names = ["-u", "--username"], required = true)
    lateinit var username: String

    @CommandLine.Option(names = ["-p", "--password"], required = true)
    lateinit var password: String
}
