# kortance

MQTT load generation made easy. :gun:

## Installation

Kortance binaries are currently available for macOS and ubuntu. Feel free to contribute if you want kortance to be
available through apt, brew or whatever package manager you prefer. :)

1. `wget https://github.com/SmartsquareGmbH/kortance/releases/download/<version>/kortance-ubuntu-latest`
2. `sudo chmod +x ./kortance-ubuntu-latest`
3. `sudo mv ./kortance-ubuntu-latest /usr/local/bin/kortance`

## Scenarios

Kortanes offers three load testing scenarios either for testing spikes, long lasting load or wave loading.

### Stress

This scenario spawns waves of clients which publish thousand messages each per default.
This can for example be used to test the (automatic) horizontal broker scaling.

![](docs/stress.png)

| Shorthand | Name          | Default | Description                                                                 |
|-----------|---------------|---------|-----------------------------------------------------------------------------|
| -u        | --username    | -       | The username used for authentication or empty if broker allows anonymous.   |
| -p        | --password    | -       | The password used for authentication or empty if broker allows anonymous.   |
| -s        | --secure      | false   | If the connection should be secured by tls.                                 |
| -w        | --waves       | 10      | The number of waves.                                                        |
| -j        | --jobs        | 10      | The amount of clients per wave.                                             |
| -m        | --messages    | 1000    | The number of messages published by each client.                            |
| -d        | --delay       | 30000   | The delay between each wave.                                                |
| -v        | --mqttVersion | V3      | The mqtt version to use. (Either V3 or V5)                                  |

### Soak

This scenario spawns a defined number of users which publish messages until the program is terminated.
This can be useful for testing the long-term behaviour or detect memory leaks.

![](docs/soak.png)

| Shorthand | Name          | Default | Description                                                                 |
|-----------|---------------|---------|-----------------------------------------------------------------------------|
| -u        | --username    | -       | The username used for authentication or empty if broker allows anonymous.   |
| -p        | --password    | -       | The password used for authentication or empty if broker allows anonymous.   |
| -s        | --secure      | false   | If the connection should be secured by tls.                                 |
| -w        | --waves       | 10      | The number of waves.                                                        |
| -j        | --jobs        | 10      | The amount of clients per wave.                                             |
| -m        | --messages    | 1000    | The number of messages published by each client.                            |
| -d        | --delay       | 30000   | The delay between each wave.                                                |
| -v        | --mqttVersion | V3      | The mqtt version to use. (Either V3 or V5)                                  |

### Spike
This scenario publishes the defined number of messages asap to test the behvaiour during load spikes.

![](docs/spike.png)

| Shorthand | Name          | Default | Description                                                                 |
|-----------|---------------|---------|-----------------------------------------------------------------------------|
| -u        | --username    | -       | The username used for authentication or empty if broker allows anonymous.   |
| -p        | --password    | -       | The password used for authentication or empty if broker allows anonymous.   |
| -s        | --secure      | false   | If the connection should be secured by tls.                                 |
| -m        | --messages    | 1000    | The number of messages published by each client.                            |
| -v        | --mqttVersion | V3      | The mqtt version to use. (Either V3 or V5)                                  |

