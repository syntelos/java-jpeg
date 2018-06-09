#!/bin/bash

function usage {
    cat<<EOF>&2
Usage

    $0 [file] [-]

Description

    Run 'jpeg' on 'file' with optional interaction.

    The jpeg file source defaults to the first listing
    from the 'test' directory.

EOF
    exit 1
}

if jarf=$(2>/dev/null ls *.jar) &&[ -n "${jarf}" ]&&[ -f "${jarf}" ]&&[ -d test ]
then
    if file=$(2>/dev/null ls test/*.jpg | head -n 1 )&&[ -n "${file}" ]&&[ -f "${file}" ]
    then
	interact=''

	while [ -n "${1}" ]
	do
	    arg="${1}"
	    shift
	    if [ '-' = "${arg}" ]
	    then
		interact='-'
	    elif [ -f "${arg}" ]&&[ -n "$(echo ${arg} | egrep '\.jpg$')" ]
	    then
		file=${arg}
	    else
		usage
	    fi
	done

	cmd="java -jar ${jarf} ${file} ${interact}"

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
