#!/bin/bash

FILES="src/main/python/saved_model_losses/*"
for f in $FILES
do
    fname="$(basename -- $f)"
    fnamenoext="${fname%.*}"

    echo "$fnamenoext"
        if [[ $fname == javascript* ]];
            then
            ./gradlew JavaScriptEvaluator -Pargs="perFileAcc $fname false"
            echo "$fname"

        elif [[ $fname == java* ]];
            then
            ./gradlew JavaEvaluator -Pargs="perFileAcc $fname"
            echo "$fname"

        elif [[ $fname == kotlin* ]];
            then
            ./gradlew KotlinEvaluator -Pargs="perFileAcc $fname"
            echo "$fname"

        elif [[ $fname == python* ]];
            then
            ./gradlew Python3Evaluator -Pargs="perFileAcc $fname"
            echo "$fname"

        elif [[ $fname == cpp* ]];
            then
            ./gradlew CPPEvaluator -Pargs="perFileAcc $fname false"
            echo "$fname"

        elif [[ $fname == csharp* ]];
            then
            ./gradlew CSharpEvaluator -Pargs="perFileAcc $fname false"
            echo "$fname"
        else
            echo "Invalid $fname"
        fi

done

