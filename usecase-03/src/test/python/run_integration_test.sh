#!/bin/bash

export CLASSPATH='../../../target/classes/:../../../target/lib/*'
export LIB_DIR='../../../target/lib'
source ../../../venv/bin/activate
if [ -d "$LIB_DIR" ]
then
	if [ "$(ls -A $LIB_DIR)" ]; then
     echo "Lib folder is not Empty"
	else
    mvn dependency:copy-dependencies -DoutputDirectory=$LIB_DIR
	fi
else
	echo "Lib folder not found."
fi

graalpython --jvm --polyglot --vm.cp=$CLASSPATH -m unittest integration_test.py