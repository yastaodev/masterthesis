#!/bin/bash
export CLASSPATH='../../../target/classes/:../../../target/lib/*'
export LIB_DIR='../../../target/lib'
source ../../../venv/bin/activate
if [ -d "$LIB_DIR" ]
then
	if [ "$(ls -A $LIB_DIR)" ]; then
     echo "$LIB_DIR is not Empty"
	else
    mvn dependency:copy-dependencies -DoutputDirectory=$LIB_DIR
	fi
else
	echo "Directory $DIR not found."
fi
graalpython --jvm --polyglot --vm.cp=$CLASSPATH -m unittest image_utils_test.py