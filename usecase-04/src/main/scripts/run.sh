#!/bin/bash
get_abs_filename() {
  # $1 : relative filename
  echo "$(cd "$(dirname "$1")" && pwd)/$(basename "$1")"
}
cd ../../..
mvn clean compile
mkdir target/lib
cd target/lib
native-image --shared -H:Name=libpwd -cp ../classes
libPath=$(get_abs_filename)
export LIBRARY_PATH=$libPath
export LD_LIBRARY_PATH=$libPath
export C_INCLUDE_PATH=$libPath
cd ../../src/main/go/src
go build -o ../../../../target/out/hashpwd
cd $libPath
../out/hashpwd
