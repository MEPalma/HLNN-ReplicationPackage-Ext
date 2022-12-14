#!/bin/bash

FILES="src/main/python/saved_model_losses/*"
for f in $FILES
do
    fname="$(basename -- $f)"
    fnamenoext="${fname%.*}"

    echo "$fnamenoext"
        if [[ $fname == javascript* ]];
            then
            ./gradlew JavaScriptEvaluator -Pargs="perFileTimeModel ../saved_model_losses/$fname false"
            echo "$fname"
        elif [[ $fname == java* ]];
            then
            ./gradlew JavaEvaluator -Pargs="perFileTimeModel ../saved_model_losses/$fname false"
            echo "$fname"

        elif [[ $fname == kotlin* ]];
            then
              ./gradlew KotlinEvaluator -Pargs="perFileTimeModel ../saved_model_losses/$fname false"
              echo "$fname"

        elif [[ $fname == python* ]];
            then
                ./gradlew Python3Evaluator -Pargs="perFileTimeModel ../saved_model_losses/$fname false"
                echo "$fname"

        elif [[ $fname == cpp* ]];
            then
            ./gradlew CPPEvaluator -Pargs="perFileTimeModel ../saved_model_losses/$fname false"
            echo "$fname"

        elif [[ $fname == csharp* ]];
            then
            ./gradlew CSharpEvaluator -Pargs="perFileTimeModel ../saved_model_losses/$fname false"
            echo "$fname"

        else
            echo "Invalid $fname"
        fi

done
