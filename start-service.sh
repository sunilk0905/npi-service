#!/usr/bin/env bash
java -Dspring.profiles.active=localhost -jar target/pnap-npi-app-0.0.1-SNAPSHOT.jar & echo $! > pnap-npi-app.pid.file &
