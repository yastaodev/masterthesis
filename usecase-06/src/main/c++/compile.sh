#!/usr/bin/env bash
get_abs_filename() {
  # $1 : relative filename
  echo "$(cd "$(dirname "$1")" && pwd)/$(basename "$1")"
}
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR
MAIN_DIR=$(realpath ../../..)
cd $MAIN_DIR/target
mkdir lib
clang++ $SCRIPT_DIR/prime.cpp -o lib/prime
