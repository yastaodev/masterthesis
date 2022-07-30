#!/bin/bash
export CLASSPATH='../../../target/classes/:../../../target/lib/*'
source ../../../venv/bin/activate
graalpython --jvm --polyglot --vm.cp=$CLASSPATH -m unittest image_utils_test.py