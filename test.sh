#!/bin/bash

if jarf=$(2>/dev/null ls *.jar) &&[ -n "${jarf}" ]&&[ -f "${jarf}" ]&&[ -d test ]
then
    if file=$(2>/dev/null ls test/*.jpg | head -n 1 )&&[ -n "${file}" ]&&[ -f "${file}" ]
    then
	cmd="java -jar ${jarf} ${file} -"

	if ${cmd}
	then
	    exit 0
	else
	    exit 1
	fi
    else
	cat<<EOF>&2
$0 error, file not found 'test/file.jpg'.
EOF
	exit 1
    fi

else
    cat<<EOF>&2
Usage

    $0

Description

    Run 
        java -jar ${jarf} test/file.jpg -

EOF
    exit 1
fi
