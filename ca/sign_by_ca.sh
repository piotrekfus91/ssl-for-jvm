#!/bin/bash

dir=`dirname $0`

if [ $# -lt 1 ] ; then
	echo "Usage: $0 csr.file"
	exit 1
fi

if [ ! -f $1 ] ; then
	echo "File not found: `realpath $1`"
	exit 2
fi

csr_path=`dirname $1`
cert_file=`echo ${1%%.*}.crt`
cert_path="$csr_path/$cert_file"

openssl x509 -req -CA $dir/ca.crt -CAkey $dir/ca.key -CAcreateserial -in $1 -out $cert_path -days 365 -passin pass:`cat $dir/ca_password.txt`
