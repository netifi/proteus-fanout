job "fanout" {

  region = "us-west"
  datacenters = ["westus2-1"]
  type = "service"

  group "count-vowels-service" {
    count = 6

    task "count-vowels-service" {
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
        image = "netifi.azurecr.io/fanout/count-vowels-service"
        network_mode = "host"
        auth {
          username = "netifi"
          password = "xv06MJfkZA17wCmy1v9e7kanMtvTsg5+"
        }
      }

      env {
        COUNT_VOWELS_SERVICE_OPTS=<<EOF
          -DBROKER_HOST=edge.prd.netifi.io
          EOF
      }
    }
  }

  group "is-vowel-service" {
    count = 6

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
          -DBROKER_HOST=edge.prd.netifi.io
          EOF
      }
    }
  }

  group "random-char-service" {
    count = 6

    task "random-char-service" {
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
        image = "netifi.azurecr.io/fanout/random-char-service"
        network_mode = "host"
        auth {
          username = "netifi"
          password = "xv06MJfkZA17wCmy1v9e7kanMtvTsg5+"
        }
      }

      env {
        RANDOM_CHAR_SERVICE_OPTS=<<EOF
          -DBROKER_HOST=edge.prd.netifi.io
          EOF
      }
    }
  }

  group "random-string-service" {
    count = 6

    task "random-string-service" {
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
        image = "netifi.azurecr.io/fanout/random-string-service"
        network_mode = "host"
        auth {
          username = "netifi"
          password = "xv06MJfkZA17wCmy1v9e7kanMtvTsg5+"
        }
      }

      env {
        RANDOM_STRING_SERVICE_OPTS=<<EOF
          -DBROKER_HOST=edge.prd.netifi.io
          EOF
      }
    }
  }

  group "fanout-client" {
    count = 1

    task "fanout-client" {
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
        image = "netifi.azurecr.io/fanout/fanout-client"
        network_mode = "host"
        auth {
          username = "netifi"
          password = "xv06MJfkZA17wCmy1v9e7kanMtvTsg5+"
        }
      }

      env {
        CLIENT_OPTS=<<EOF
          -DBROKER_HOST=edge.prd.netifi.io
          -DNUM_VOWELS=100000000
          -DMIN_HOSTS_AT_STARTUP=4
          -DPOOL_SIZE=4
          EOF
      }
    }
  }
}