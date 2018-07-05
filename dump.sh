#!/bin/bash

if rabu=$(2>/dev/null ls lib/java-rabu-*.jar )&&[ -n "${rabu}" ]
then
  java -jar ${rabu} $* 
else
  exit 1
fi
