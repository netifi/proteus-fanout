job "is-vowel-delay" {

  region = "us-west"
  datacenters = ["westus2-1"]
  type = "service"

  group "is-vowel-service" {
    count = 1

    task "is-vowel-service" {
      driver = "docker"

      logs {
        max_files     = 10
        max_file_size = 15
      }

      resources {
        cpu    = 1000
        memory = 2000
        network {
          mbits = 100
        }
      }

      config {
        image = "netifi.azurecr.io/fanout/is-vowel-service"
        network_mode = "host"
        auth {
          username = "netifi"
          password = "xv06MJfkZA17wCmy1v9e7kanMtvTsg5+"
        }
      }

      env {
        IS_VOWEL_SERVICE_OPTS=<<EOF
          -DROUTER_HOST=edge.prd.netifi.io
          -Ddelayed=true
          -DLOW=10
          -DHIGH=100
          EOF
      }
    }
  }

}