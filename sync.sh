#!/bin/bash
#
function sources {
    srcd=../java-rabu/src/syntelos/rabu
    tgtd=src/syntelos/rabu
    query='(Buffer|Data)'


    for srcf in $(2>/dev/null ls ${srcd}/* | egrep -e "${query}" )
    do

	if [ -n "${srcf}" ]&&[ -f "${srcf}" ]
	then
	    name=$(basename ${srcf})
	    tgtf=${tgtd}/${name}

	    if [ -f ${tgtf} ]
	    then

		if [ ${srcf} -nt ${tgtf} ]&&[ -n "$(diff ${srcf} ${tgtf} )" ]
		then
		    cp -p ${srcf} ${tgtf}
		    echo "U ${tgtf}"
		else
		    echo "N ${tgtf}"
		fi
	    else
		cp -p ${srcf} ${tgtf}
		git add ${tgtf}
		echo "A ${tgtf}"
	    fi
	else
	    cat<<EOF>&2
$0 error listing '${srcd}'.
EOF
	    return 1
	fi
    done
    return 0
}
#
function targets {
    srcd=../java-rabu
    tgtd=./lib

    for srcf in $(2>/dev/null ls ${srcd}/*.jar)
    do
	if [ -n "${srcf}" ]&&[ -f "${srcf}" ]
	then
	    name=$(basename ${srcf})
	    tgtf=${tgtd}/${name}

	    if [ -f ${tgtf} ]
	    then

		if [ ${srcf} -nt ${tgtf} ]
		then
		    cp -p ${srcf} ${tgtf}
		    echo "U ${tgtf}"
		else
		    echo "N ${tgtf}"
		fi
	    else
		cp -p ${srcf} ${tgtf}
		git add ${tgtf}
		echo "A ${tgtf}"
	    fi
	else
	    cat<<EOF>&2
$0 error listing '${srcd}'.
EOF
	    return 1
	fi
    done
}

targets
